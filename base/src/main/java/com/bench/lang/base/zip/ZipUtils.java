package com.bench.lang.base.zip;

import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;
import com.bench.lang.base.charset.utils.CharsetUtils;
import com.bench.lang.base.string.utils.StringUtils;
import com.bench.lang.base.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.zip.*;

public class ZipUtils {

	private static final Logger log = LoggerFactory.getLogger(ZipUtils.class);

	/**
	 * 
	 * @param zipFilePath
	 * @param entryName
	 * @return
	 */
	public static byte[] getEntryData(String zipFilePath, String entryName) {
		return getEntryData(zipFilePath, CharsetUtils.UTF_8, entryName);
	}

	/**
	 * 获取条目数据
	 * 
	 * @param zipFilePath
	 * @param charset
	 * @param entryName
	 * @return
	 */
	public static byte[] getEntryData(String zipFilePath, Charset charset, String entryName) {
		byte[] content = null;
		try (FileInputStream fis = new FileInputStream(new File(zipFilePath))) {
			content = IOUtils.toByteArray(fis);
		} catch (IOException e) {
			log.error("读取文件内容异常，zipFilePath=" + zipFilePath);
			return null;
		}
		return getEntryData(content, charset, entryName);
	}

	/**
	 * 获取条目数据
	 * 
	 * @param zipContent
	 * @param entryName
	 * @return
	 */
	public static byte[] getEntryData(byte[] zipContent, String entryName) {
		return getEntryData(zipContent, CharsetUtils.UTF_8, entryName);
	}

	/**
	 * 获取条目数据
	 * 
	 * @param zipContent
	 * @param charset
	 * @param entryName
	 * @return
	 */
	public static byte[] getEntryData(byte[] zipContent, Charset charset, String entryName) {

		ZipEntry entry = null;
		try (ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipContent), charset);) {
			while ((entry = zipInputStream.getNextEntry()) != null) {
				if (entry != null && StringUtils.equals(entry.getName(), entryName)) {
					return IOUtils.toByteArray(zipInputStream);
				}
			}
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 访问压缩文件
	 * 
	 * @param zipContent
	 * @param visitor
	 * @throws IOException
	 */
	public static void visit(byte[] zipContent, ZipEntryVisitor visitor) throws IOException {
		visit(zipContent, CharsetUtils.UTF_8, visitor);
	}

	/**
	 * 访问压缩文件
	 * 
	 * @param zipContent
	 * @param charset
	 * @param visitor
	 * @throws IOException
	 */
	public static void visit(byte[] zipContent, Charset charset, ZipEntryVisitor visitor) throws IOException {
		ZipEntry entry = null;
		try (ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipContent), charset);) {
			while ((entry = zipInputStream.getNextEntry()) != null) {
				visitor.visit(entry, zipInputStream);
				if (!visitor.visitNext()) {
					break;
				}
			}
		}
	}

	/**
	 * 解压文件
	 * 
	 * @param zipContent
	 * @param extractPath
	 * @throws IOException
	 */
	public static void extract(byte[] zipContent, String extractPath) throws IOException {
		extract(zipContent, CharsetUtils.UTF_8, extractPath);
	}

	/**
	 * 解压文件
	 * 
	 * @param zipContent
	 * @param charset
	 * @param extractPath
	 * @throws IOException
	 */
	public static void extract(byte[] zipContent, Charset charset, String extractPath) throws IOException {
		visit(zipContent, charset, new ZipEntryVisitor() {
			@Override
			public void visit(ZipEntry zipEntry, ZipInputStream is) throws IOException {
				// TODO Auto-generated method stub
				File path = new File(extractPath, zipEntry.getName());
				// 如果是目录，在创建目录
				if (zipEntry.isDirectory()) {
					if (!path.exists()) {
						path.mkdirs();
					}
				}
				// 文件，则写入文件
				else {
					if (!path.getParentFile().exists()) {
						path.getParentFile().mkdirs();
					}
					try (FileOutputStream fos = new FileOutputStream(path);) {
						IOUtils.copy(is, fos);
						IOUtils.flush(fos);
					}
				}
			}
		});
	}

	/**
	 * 访问压缩文件
	 * 
	 * @param zipFile
	 * @param visitor
	 * @throws IOException
	 */
	public static void visit(File zipFile, ZipEntryVisitor visitor) throws IOException {
		visit(zipFile, CharsetUtils.UTF_8, visitor);
	}

	/**
	 * 访问压缩文件
	 * 
	 * @param zipFile
	 * @param charset
	 * @param visitor
	 * @throws IOException
	 */
	public static void visit(File zipFile, Charset charset, ZipEntryVisitor visitor) throws IOException {
		ZipEntry entry = null;
		try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile), charset);) {
			while ((entry = zipInputStream.getNextEntry()) != null) {
				visitor.visit(entry, zipInputStream);
				if (!visitor.visitNext()) {
					break;
				}
			}
		}
	}

	/**
	 * 访问压缩文件
	 * 
	 * @param zipFile
	 * @param visitor
	 * @throws IOException
	 */
	public static void visit(String zipFile, ZipEntryVisitor visitor) throws IOException {
		visit(new File(zipFile), visitor);
	}

	/**
	 * 访问压缩文件
	 * 
	 * @param zipFile
	 * @param visitor
	 * @throws IOException
	 */
	public static void visit(String zipFile, Charset charset, ZipEntryVisitor visitor) throws IOException {
		visit(new File(zipFile), charset, visitor);
	}

	/**
	 * 解压文件
	 * 
	 * @param zipFile
	 * @param extractPath
	 * @throws IOException
	 */
	public static void extract(File zipFile, String extractPath) throws IOException {
		extract(zipFile, CharsetUtils.UTF_8, extractPath);
	}

	/**
	 * 解压文件
	 * 
	 * @param zipFile
	 * @param charset
	 * @param extractPath
	 * @throws IOException
	 */
	public static void extract(File zipFile, Charset charset, String extractPath) throws IOException {
		visit(zipFile, charset, new ZipEntryVisitor() {
			@Override
			public void visit(ZipEntry zipEntry, ZipInputStream is) throws IOException {
				// TODO Auto-generated method stub
				File path = new File(extractPath, zipEntry.getName());
				// 如果是目录，在创建目录
				if (zipEntry.isDirectory()) {
					if (!path.exists()) {
						path.mkdirs();
					}
				}
				// 文件，则写入文件
				else {
					if (!path.getParentFile().exists()) {
						path.getParentFile().mkdirs();
					}
					try (FileOutputStream fos = new FileOutputStream(path);) {
						IOUtils.copy(is, fos);
						IOUtils.flush(fos);
					}
				}
			}
		});
	}

	/**
	 * 增加数据
	 * 
	 */
	public static void addData(ZipOutputStream zos, String name, byte[] content, String comment, Date gmtModified) {
		ZipEntry dataZipEntry = new ZipEntry(name);
		if (gmtModified != null) {
			dataZipEntry.setTime(gmtModified.getTime());
		}
		if (!StringUtils.isEmpty(comment)) {
			dataZipEntry.setComment(comment);
		}
		try {
			zos.putNextEntry(dataZipEntry);
			zos.write(content);
			zos.closeEntry();
		} catch (IOException e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "增加zip数据异常,name=" + name, e);
		}
	}

	/**
	 * 增加数据
	 * 
	 * @param zos
	 * @param name
	 * @param content
	 * @param gmtModified
	 */
	public static void addData(ZipOutputStream zos, String name, byte[] content, Date gmtModified) {
		addData(zos, name, content, null, gmtModified);
	}

	/**
	 * 增加数据
	 * 
	 * @param zos
	 * @param name
	 * @param content
	 */
	public static void addData(ZipOutputStream zos, String name, byte[] content) {
		addData(zos, name, content, null, null);
	}

	/**
	 * 获取第一个entry
	 * 
	 * @param zipContent
	 * @return
	 */
	public static byte[] getFistEntryData(byte[] zipContent) {
		return getFistEntryData(zipContent, CharsetUtils.UTF_8);
	}

	/**
	 * 获取第一个entry
	 * 
	 * @param zipContent
	 * @param charset
	 * @return
	 */
	public static byte[] getFistEntryData(byte[] zipContent, Charset charset) {
		try (ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipContent), charset)) {
			ZipEntry entry = zipInputStream.getNextEntry();
			while (entry != null) {
				if (!entry.isDirectory()) {
					return IOUtils.toByteArray(zipInputStream);
				} else {
					entry = zipInputStream.getNextEntry();
				}
			}
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public static byte[] extraGZIP(byte[] zipContent) {
		try (GZIPInputStream zipInputStream = new GZIPInputStream(new ByteArrayInputStream(zipContent))) {
			return IOUtils.toByteArray(zipInputStream);
		} catch (Exception e) {
			log.error("GZIP解压异常", e);
			return null;
		}
	}

	/**
	 * 压缩
	 * 
	 * @param data
	 *            待压缩数据
	 * @return byte[] 压缩后的数据
	 */
	public static byte[] compress(byte[] data) {
		byte[] output = new byte[0];

		Deflater compresser = new Deflater();
		compresser.reset();
		compresser.setInput(data);
		compresser.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!compresser.finished()) {
				int i = compresser.deflate(buf);
				bos.write(buf, 0, i);
			}
			output = bos.toByteArray();
		} catch (Exception e) {
			output = data;
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		compresser.end();
		return output;
	}

	/**
	 * 压缩
	 * 
	 * @param data
	 *            待压缩数据
	 * 
	 * @param os
	 *            输出流
	 */
	public static void compress(byte[] data, OutputStream os) {
		DeflaterOutputStream dos = new DeflaterOutputStream(os);

		try {
			dos.write(data, 0, data.length);

			dos.finish();

			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压缩
	 * 
	 * @param data
	 *            待压缩的数据
	 * @return byte[] 解压缩后的数据
	 */
	public static byte[] decompress(byte[] data) {
		byte[] output = new byte[0];

		Inflater decompresser = new Inflater();
		decompresser.reset();
		decompresser.setInput(data);

		ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!decompresser.finished()) {
				int i = decompresser.inflate(buf);
				o.write(buf, 0, i);
			}
			output = o.toByteArray();
		} catch (Exception e) {
			output = data;
			e.printStackTrace();
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		decompresser.end();
		return output;
	}

	/**
	 * 解压缩
	 * 
	 * @param is
	 *            输入流
	 * @return byte[] 解压缩后的数据
	 */
	public static byte[] decompress(InputStream is) {
		InflaterInputStream iis = new InflaterInputStream(is);
		ByteArrayOutputStream o = new ByteArrayOutputStream(1024);
		try {
			int i = 1024;
			byte[] buf = new byte[i];

			while ((i = iis.read(buf, 0, i)) > 0) {
				o.write(buf, 0, i);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return o.toByteArray();
	}

	public static void close(final ZipFile zipFile) {
		if (zipFile != null) {
			try {
				zipFile.close();
			} catch (IOException ioex) {
				// ignore
			}
		}
	}
}
