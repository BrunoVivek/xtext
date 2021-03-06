/*******************************************************************************
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

define(["xtext/services/AbstractXtextService", "orion/Deferred"], function(AbstractXtextService, Deferred) {

	function ContentAssistService(serverUrl, resourceUri, updateService) {
		this.initialize(serverUrl, resourceUri, "content-assist");
		this._updateService = updateService;
	}

	ContentAssistService.prototype = new AbstractXtextService();
		
	ContentAssistService.prototype.computeContentAssist = function(editorContext, params, deferred) {
		if (deferred === undefined) {
			deferred = new Deferred();
		}
		var serverData = {
			contentType : params.contentType,
			caretOffset : params.offset
		};
		if (params.selection.start != params.offset || params.selection.end != params.offset) {
			serverData.selectionStart = params.selection.start;
			serverData.selectionEnd = params.selection.end;
		}
		var currentText = editorContext.getText();
		var updateServerState = false;
		var httpMethod = "GET";
		var onComplete = undefined;
		if (params.sendFullText) {
			serverData.fullText = editorContext.getText();
			httpMethod = "POST";
		} else {
			var knownServerState = editorContext.getServerState();
			if (knownServerState.stateId !== undefined) {
				serverData.requiredStateId = knownServerState.stateId;
			}
			if (this._updateService && knownServerState.text !== undefined) {
				if (this._updateService.checkRunningUpdate()) {
					var self = this;
					this._updateService.addCompletionCallback(function() {
						self.computeContentAssist(editorContext, params, deferred);
					});
					return deferred.promise;
				}
				onComplete = this._updateService.onComplete.bind(this._updateService);
				this._updateService.computeDelta(knownServerState.text, currentText, serverData);
				if (serverData.deltaText !== undefined) {
					httpMethod = "POST";
					updateServerState = true;
				}
			}
		}
		
		var self = this;
		self.sendRequest(editorContext, {
			type : httpMethod,
			data : serverData,
			success : function(result) {
				if (result.conflict) {
					// A conflict with another service occured - retry
					self.retry(self.computeContentAssist, editorContext, params, [deferred]);
					return false;
				}
				if (updateServerState) {
					editorContext.updateServerState(currentText, result.stateId);
				}
				var proposals = [];
				for (var i = 0; i < result.entries.length; i++) {
					var e = result.entries[i];
					proposals.push({
						proposal : e.proposal,
						prefix : result.prefix,
						overwrite : true,
						name : e.name,
						description : e.description,
						style : e.style,
						additionalEdits : e.textReplacements,
						positions : e.editPositions
					});
				}
				deferred.resolve(proposals);
			},
			error : function(xhr, textStatus, errorThrown) {
				deferred.reject(errorThrown);
			},
			complete: onComplete
		});
		return deferred.promise;
	};
	
	return ContentAssistService;
});
