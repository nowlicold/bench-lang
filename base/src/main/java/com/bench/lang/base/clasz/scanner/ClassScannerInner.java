package com.bench.lang.base.clasz.scanner;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.yuan.common.enums.error.CommonErrorCodeEnum;
import com.yuan.common.exception.BenchRuntimeException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.SystemPropertyUtils;

import com.bench.lang.base.accept.Acceptor;
import com.bench.lang.base.clasz.visit.ClassMetadataVisitor;
import com.bench.lang.base.clasz.visit.SimpleClassVisitor;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * 类扫描器内部类
 * 
 * @author cold
 *
 * @version $Id: ClassScClassScannerInneranner.java, v 0.1 2018年10月12日 上午10:19:08 cold Exp $
 */
public class ClassScannerInner {

	protected ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	protected MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);

	public ResourcePatternResolver getResourcePatternResolver() {
		return resourcePatternResolver;
	}

	public MetadataReaderFactory getMetadataReaderFactory() {
		return metadataReaderFactory;
	}

	public Set<Class<?>> scan(String[] basePackages, Acceptor<MetadataReader> classAcceptor) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		for (String s : basePackages)
			classes.addAll(scan(s, classAcceptor));
		return classes;
	}

	public Set<Class<?>> scan(String basePackage, Acceptor<MetadataReader> classAcceptor) {
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		scan(basePackage, classAcceptor, new SimpleClassVisitor() {
			@Override
			public void visitClass(Class<?> clasz) {
				// TODO Auto-generated method stub
				classes.add(clasz);
			}

		});
		return classes;
	}

	/**
	 * @param basePackages
	 * @param classAcceptor
	 * @param visitor
	 */
	public void scan(String[] basePackages, Acceptor<MetadataReader> classAcceptor, SimpleClassVisitor visitor) {
		for (String basePackage : basePackages) {
			scan(basePackage, classAcceptor, visitor);
		}
	}

	/**
	 * @param basePackages
	 * @param visitor
	 */
	public void scan(String[] basePackages, SimpleClassVisitor visitor) {
		for (String basePackage : basePackages) {
			scan(basePackage, visitor);
		}
	}

	public void scan(String basePackage, SimpleClassVisitor visitor) {
		scan(basePackage, null, visitor);
	}

	/**
	 * @param basePackage
	 * @param classAcceptor
	 * @param visitor
	 */
	public void scan(String basePackage, Acceptor<MetadataReader> classAcceptor, SimpleClassVisitor visitor) {
		scan(basePackage, new ClassMetadataVisitor() {
			@Override
			public void visit(MetadataReader metadataReader) {
				// TODO Auto-generated method stub
				if (metadataReader.getClassMetadata() != null && (classAcceptor == null || classAcceptor.accept(metadataReader))) {
					try {
						visitor.visitClass(Class.forName(metadataReader.getClassMetadata().getClassName()));
					} catch (Exception e) {
						// 忽略异常
					} catch (NoClassDefFoundError e) {
						throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "载入类异常,className=" + metadataReader.getClassMetadata().getClassName(), e);
					}
				}
			}
		});
	}

	/**
	 * 访问特定包下的所有类元数据
	 * @param basePackage
	 * @param visitor
	 */
	public void scan(String basePackage, ClassMetadataVisitor visitor) {
		String scanPackage = basePackage;
		// 去掉最后一个.
		if (StringUtils.endsWith(basePackage, StringUtils.DOT_SIGN)) {
			scanPackage = basePackage.substring(0, basePackage.length() - 1);
		}
		try {
			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
					+ org.springframework.util.ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(scanPackage)) + "/**/*.class";
			Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
			for (int i = 0; i < resources.length; i++) {
				Resource resource = resources[i];
				if (resource.isReadable()) {
					MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
					visitor.visit(metadataReader);
				}
			}
		} catch (IOException ex) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR,"I/O failure during classpath scanning", ex);
		}
	}
}