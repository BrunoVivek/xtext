/**
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.web.server.persistence;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import java.io.IOException;
import org.eclipse.xtext.parser.IEncodingProvider;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.eclipse.xtext.web.server.ISessionStore;
import org.eclipse.xtext.web.server.InvalidRequestException;
import org.eclipse.xtext.web.server.data.JsonObject;
import org.eclipse.xtext.web.server.data.ResourceContent;
import org.eclipse.xtext.web.server.model.XtextDocument;
import org.eclipse.xtext.web.server.persistence.IServerResourceHandler;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Pair;

@Singleton
@SuppressWarnings("all")
public class ResourcePersistenceService {
  @Inject
  private Provider<XtextResourceSet> resourceSetProvider;
  
  @Inject
  private IEncodingProvider encodingProvider;
  
  @Inject
  private XtextDocument.CreationLock creationLock;
  
  public ResourceContent load(final String resourceId, final IServerResourceHandler resourceHandler, final ISessionStore sessionStore) throws InvalidRequestException {
    ResourceContent _xblockexpression = null;
    {
      XtextDocument _xsynchronizedexpression = null;
      synchronized (this.creationLock) {
        Pair<Class<XtextDocument>, String> _mappedTo = Pair.<Class<XtextDocument>, String>of(XtextDocument.class, resourceId);
        final Function0<XtextDocument> _function = new Function0<XtextDocument>() {
          @Override
          public XtextDocument apply() {
            try {
              XtextDocument _xblockexpression = null;
              {
                final XtextResourceSet resourceSet = ResourcePersistenceService.this.resourceSetProvider.get();
                XtextDocument _xtrycatchfinallyexpression = null;
                try {
                  _xtrycatchfinallyexpression = resourceHandler.get(resourceId, resourceSet);
                } catch (final Throwable _t) {
                  if (_t instanceof IOException) {
                    final IOException ioe = (IOException)_t;
                    throw new InvalidRequestException(404, "The requested resource was not found.");
                  } else {
                    throw Exceptions.sneakyThrow(_t);
                  }
                }
                _xblockexpression = _xtrycatchfinallyexpression;
              }
              return _xblockexpression;
            } catch (Throwable _e) {
              throw Exceptions.sneakyThrow(_e);
            }
          }
        };
        _xsynchronizedexpression = ISessionStore.Extensions.<XtextDocument>get(sessionStore, _mappedTo, _function);
      }
      final XtextDocument document = _xsynchronizedexpression;
      final IUnitOfWork<ResourceContent, XtextDocument.ReadAccess> _function = new IUnitOfWork<ResourceContent, XtextDocument.ReadAccess>() {
        @Override
        public ResourceContent exec(final XtextDocument.ReadAccess access) throws Exception {
          String _text = access.getText();
          final ResourceContent result = new ResourceContent(_text);
          boolean _isDirty = access.isDirty();
          result.setDirty(_isDirty);
          String _stateId = access.getStateId();
          result.setStateId(_stateId);
          return result;
        }
      };
      _xblockexpression = document.<ResourceContent>readOnly(_function);
    }
    return _xblockexpression;
  }
  
  public ResourceContent revert(final String resourceId, final String newStateId, final IServerResourceHandler resourceHandler, final ISessionStore sessionStore) throws InvalidRequestException {
    ResourceContent _xblockexpression = null;
    {
      final XtextResourceSet resourceSet = this.resourceSetProvider.get();
      ResourceContent _xtrycatchfinallyexpression = null;
      try {
        ResourceContent _xblockexpression_1 = null;
        {
          final XtextDocument document = resourceHandler.get(resourceId, resourceSet);
          final IUnitOfWork<ResourceContent, XtextDocument.ModifyAccess> _function = new IUnitOfWork<ResourceContent, XtextDocument.ModifyAccess>() {
            @Override
            public ResourceContent exec(final XtextDocument.ModifyAccess access) throws Exception {
              Pair<Class<XtextDocument>, String> _mappedTo = Pair.<Class<XtextDocument>, String>of(XtextDocument.class, resourceId);
              sessionStore.put(_mappedTo, document);
              access.setStateId(newStateId);
              access.setDirty(false);
              String _text = access.getText();
              final ResourceContent result = new ResourceContent(_text);
              result.setStateId(newStateId);
              return result;
            }
          };
          _xblockexpression_1 = document.<ResourceContent>modify(_function);
        }
        _xtrycatchfinallyexpression = _xblockexpression_1;
      } catch (final Throwable _t) {
        if (_t instanceof IOException) {
          final IOException ioe = (IOException)_t;
          throw new InvalidRequestException(404, "The requested resource was not found.");
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      _xblockexpression = _xtrycatchfinallyexpression;
    }
    return _xblockexpression;
  }
  
  public JsonObject save(final XtextDocument document, final IServerResourceHandler resourceHandler, final String requiredStateId) throws InvalidRequestException {
    final IUnitOfWork<JsonObject, XtextDocument.ModifyAccess> _function = new IUnitOfWork<JsonObject, XtextDocument.ModifyAccess>() {
      @Override
      public JsonObject exec(final XtextDocument.ModifyAccess access) throws Exception {
        access.checkStateId(requiredStateId);
        try {
          resourceHandler.put(access, ResourcePersistenceService.this.encodingProvider);
          access.setDirty(false);
        } catch (final Throwable _t) {
          if (_t instanceof IOException) {
            final IOException ioe = (IOException)_t;
            String _message = ioe.getMessage();
            throw new InvalidRequestException(404, _message);
          } else {
            throw Exceptions.sneakyThrow(_t);
          }
        }
        return new JsonObject();
      }
    };
    return document.<JsonObject>modify(_function);
  }
}
