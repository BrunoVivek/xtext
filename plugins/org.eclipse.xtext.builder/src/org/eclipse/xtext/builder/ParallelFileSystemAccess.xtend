package org.eclipse.xtext.builder

import java.io.InputStream
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.xtext.generator.AbstractFileSystemAccess
import org.eclipse.xtext.generator.FileSystemAccessQueue
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IFileSystemAccessExtension
import org.eclipse.xtext.generator.IFileSystemAccessExtension2
import org.eclipse.xtext.generator.IFileSystemAccessExtension3
import org.eclipse.xtext.resource.IResourceDescription.Delta
import org.eclipse.xtext.util.RuntimeIOException
import org.eclipse.xtext.generator.IFileSystemAccessExtension4

/**
 * @author Anton Kosyakov
 * @since 2.7
 */
class ParallelFileSystemAccess implements IFileSystemAccess, IFileSystemAccessExtension, IFileSystemAccessExtension2, IFileSystemAccessExtension3, IFileSystemAccessExtension4 {

	Delta delta
	
	String sourceFolder
	
	IFileSystemAccess delegate

	FileSystemAccessQueue fileSystemAccessQueue

	EclipseResourceFileSystemAccess2.IFileCallback fileCallback

	new(IFileSystemAccess delegate, Delta delta, FileSystemAccessQueue fileSystemAccessQueue, String sourceFolder, EclipseResourceFileSystemAccess2.IFileCallback fileCallback) {
		this.delta = delta
		this.delegate = delegate
		this.fileCallback = fileCallback
		this.sourceFolder = sourceFolder
		this.fileSystemAccessQueue = fileSystemAccessQueue
	}
	
	protected def <T> sendAsync((T)=>void procedure) {
		fileSystemAccessQueue.sendAsync(delta.uri) [ |
			if (delegate instanceof EclipseResourceFileSystemAccess2) {
				delegate.postProcessor = fileCallback
			}
			if (sourceFolder != null) {
				if (delegate instanceof AbstractFileSystemAccess) {	
					delegate.currentSource = sourceFolder
				}
			}
			procedure.apply(delegate as T)
		]
	}

	override deleteFile(String fileName) {
		sendAsync [ IFileSystemAccess access |
			access.deleteFile(fileName)
		]
	}

	override generateFile(String fileName, CharSequence contents) {
		sendAsync [ IFileSystemAccess access |
			access.generateFile(fileName, contents)
		]
	}

	override generateFile(String fileName, String outputConfigurationName, CharSequence contents) {
		sendAsync [ IFileSystemAccess access |
			access.generateFile(fileName, outputConfigurationName, contents)
		]
	}
	
	override deleteFile(String fileName, String outputConfigurationName) {
		sendAsync [ IFileSystemAccessExtension access |
			access.deleteFile(fileName, outputConfigurationName)
		]
	}
	
	override getURI(String path, String outputConfiguration) {
		if (delegate instanceof EclipseResourceFileSystemAccess2) {
			return delegate.getURI(path, outputConfiguration, new NullProgressMonitor)
		}
		return (delegate as IFileSystemAccessExtension2).getURI(path, outputConfiguration)
	}
	
	override getURI(String path) {
		if (delegate instanceof EclipseResourceFileSystemAccess2) {
			return delegate.getURI(path, new NullProgressMonitor)
		}
		return (delegate as IFileSystemAccessExtension2).getURI(path)
	}
	
	override generateFile(String fileName, String outputCfgName, InputStream content) throws RuntimeIOException {
		sendAsync [ IFileSystemAccessExtension3 access |
			access.generateFile(fileName, outputCfgName, content)
		]
	}
	
	override generateFile(String fileName, InputStream content) throws RuntimeIOException {
		sendAsync [ IFileSystemAccessExtension3 access |
			access.generateFile(fileName, content)
		]
	}
	
	override readBinaryFile(String fileName, String outputCfgName) throws RuntimeIOException {
		if (delegate instanceof EclipseResourceFileSystemAccess2) {
			return delegate.readBinaryFile(fileName, outputCfgName, new NullProgressMonitor)
		}
		return (delegate as IFileSystemAccessExtension3).readBinaryFile(fileName, outputCfgName)
	}
	
	override readBinaryFile(String fileName) throws RuntimeIOException {
		if (delegate instanceof EclipseResourceFileSystemAccess2) {
			return delegate.readBinaryFile(fileName, new NullProgressMonitor)
		}
		return (delegate as IFileSystemAccessExtension3).readBinaryFile(fileName)
	}
	
	override readTextFile(String fileName, String outputCfgName) throws RuntimeIOException {
		if (delegate instanceof EclipseResourceFileSystemAccess2) {
			return delegate.readTextFile(fileName, outputCfgName, new NullProgressMonitor)
		}
		return (delegate as IFileSystemAccessExtension3).readTextFile(fileName, outputCfgName)
	}
	
	override readTextFile(String fileName) throws RuntimeIOException {
		if (delegate instanceof EclipseResourceFileSystemAccess2) {
			return delegate.readTextFile(fileName, new NullProgressMonitor)
		}
		return (delegate as IFileSystemAccessExtension3).readTextFile(fileName)
	}

	/**
	 * @since 2.9
	 */
	override isFile(String path, String outputConfigurationName) throws RuntimeIOException {
		if (delegate instanceof EclipseResourceFileSystemAccess2) {
			return delegate.isFile(path, outputConfigurationName, new NullProgressMonitor)
		}
		if (delegate instanceof IFileSystemAccessExtension4) {
			return (delegate as IFileSystemAccessExtension4).isFile(path, outputConfigurationName)
		}
		return false
	}

}
