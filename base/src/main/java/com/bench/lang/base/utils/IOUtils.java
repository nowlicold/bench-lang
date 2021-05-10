/**
 *
 */
package com.bench.lang.base.utils;

import com.bench.lang.base.Constants;
import com.bench.lang.base.charset.utils.CharsetUtils;
import com.bench.lang.base.string.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;


/**
 * @author cold 2021/05/11 上午12:32:48
 *
 */
public class IOUtils extends org.apache.commons.io.IOUtils {

    public static void flush(Flushable flushable) {
        try {
            if (flushable != null)
                flushable.flush();
        } catch (IOException e) {
            // 忽略错误
        }
    }

    public static byte[] getFileBytes(String filePath) throws IOException {
        return getFileBytes(new File(filePath));
    }

    public static byte[] getFileBytes(File file) throws IOException {
        return toByteArray(new FileInputStream(file));
    }

    public static String getFileString(File file, String charset) throws IOException {
        return new String(getFileBytes(file), charset);
    }

    public static String getFileString(String filePath, String charset) throws IOException {
        return new String(getFileBytes(filePath), charset);
    }

    public static String getFileString(File file, Charset charset) throws IOException {
        return new String(getFileBytes(file), charset);
    }

    public static String getFileString(String filePath, Charset charset) throws IOException {
        return new String(getFileBytes(filePath), charset);
    }

    /**
     * @param content
     * @param file
     * @throws IOException
     */
    public static void writeFile(byte[] content, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content);
        }
    }

    /**
     * @param content
     * @param file
     * @throws IOException
     */
    public static void writeFile(String content, Charset charset, File file) throws IOException {
        try (FileWriter fr = new FileWriter(file, charset)) {
            fr.append(content);
        }
    }

    /**
     * @param content
     * @param filePath
     */
    public static boolean write(String content, String filePath, boolean ignoreExists) throws IOException {
        return write(content, new File(filePath), ignoreExists);

    }

    /**
     * 写入content到file，如果文件已经存在，并且ignoreExists为true，则不写入
     *
     * @param content
     * @param file
     * @param ignoreExists
     * @return
     * @throws IOException
     */
    public static boolean write(String content, File file, boolean ignoreExists) throws IOException {
        if (file.exists() && ignoreExists)
            return true;
        if (!file.getParentFile().exists()) {
            new File(file.getParent()).mkdirs();
        }
        try (FileWriter fw = new FileWriter(file);) {
            IOUtils.write(content, fw);
            IOUtils.flush(fw);
        }
        return true;

    }

    public static boolean write(String content, File file, String charset, boolean ignoreExists) throws IOException {
        if (file.exists() && ignoreExists)
            return true;
        if (!file.getParentFile().exists()) {
            new File(file.getParent()).mkdirs();
        }
        try (FileWriter fw = new FileWriter(file, Charset.forName(StringUtils.defaultIfEmpty(charset, Constants.DEFAULT_CHARSET)));) {
            IOUtils.write(content, fw);
            IOUtils.flush(fw);
        }
        return true;

    }

    /**
     * @param content
     * @param filePath
     */
    public static boolean write(byte[] content, String filePath, boolean ignoreExists) throws IOException {
        return write(content, new File(filePath), ignoreExists);
    }

    public static boolean write(byte[] content, File file, boolean ignoreExists) throws IOException {

        if (file.exists() && ignoreExists)
            return true;

        if (!file.getParentFile().exists()) {
            new File(file.getParent()).mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(file);) {
            IOUtils.write(content, fos);
            IOUtils.flush(fos);
        }
        return true;

    }

    /**
     * 转换为String，注意，因为大部分编码不是UTF-8就是GBK，所以这里判断如果是UTF-8，则用UTF-8，否则使用默认编码
     *
     * @param input
     * @return
     * @throws IOException
     */
    public static String toString(final InputStream input) throws IOException {
        byte[] data = toByteArray(input);
        if (CharsetUtils.isUtf8Charset(data)) {
            return new String(data, "UTF-8");
        } else {
            return new String(data, Charset.defaultCharset());
        }

    }

}
