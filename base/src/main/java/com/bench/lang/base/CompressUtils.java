package com.bench.lang.base;


import com.bench.lang.base.utils.IOUtils;
import org.apache.commons.compress.compressors.brotli.BrotliCompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


/**
 * 压缩工具类
 * 
 * @author chenbug
 * 
 * @version $Id: CompressUtils.java, v 0.1 2012-10-31 下午2:22:48 chenbug Exp $
 */
public class CompressUtils {

	private static final Logger log = LoggerFactory.getLogger(CompressUtils.class);

	/**
	 * 将dataIs数据流进行Bzip2压缩后，输出到zipOs流中
	 * 
	 * @param dataIs
	 * @param zipOs
	 * @throws IOException
	 */
	public static void bzip2Compress(InputStream dataIs, OutputStream zipOs) throws IOException {
		BZip2CompressorOutputStream os = new BZip2CompressorOutputStream(zipOs);
		try {
			IOUtils.copy(dataIs, os);
			os.flush();
			os.finish();
		} finally {
			IOUtils.closeQuietly(os);
		}
	}

	/**
	 * 将data数据进行Bzip2压缩后，返回压缩后的byte数组
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static byte[] bzip2Compress(byte[] data) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ByteArrayInputStream dataIs = new ByteArrayInputStream(data);
		try {
			bzip2Compress(dataIs, bos);
			return bos.toByteArray();
		} finally {
			IOUtils.closeQuietly(dataIs);
			IOUtils.closeQuietly(bos);
		}
	}

	/**
	 * 解压BR
	 * 
	 * @param zipContent
	 * @return
	 */
	public static byte[] extraBR(byte[] zipContent) {
		BrotliCompressorInputStream brInputStream = null;
		try {
			brInputStream = new BrotliCompressorInputStream(new ByteArrayInputStream(zipContent));
			return IOUtils.toByteArray(brInputStream);
		} catch (Exception e) {
			log.error("BR解压异常", e);
			return null;
		} finally {
			IOUtils.closeQuietly(brInputStream);
		}
	}
}
