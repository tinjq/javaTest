package com.tin.java.core.io.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tin.utils.ResourceUtil;
import org.junit.Test;

/**
 * 一、通道（Channel）: 用于源节点与目标节点的连接，在 Java NIO 中负责缓冲区中数据的传输。
 * 		Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 * 
 * 二、通道的主要实现类
 * 	java.nio.channels.Channel 接口：
 * 		|--FileChannel
 * 		|--SocketChannel
 * 		|--ServerSocketChannel
 * 		|--DatagramChannel
 * 
 * 三、获取通道
 * 1. Java 针对支持通道的类提供了 getChannel() 方法
 * 		本地 IO:
 * 		FileInputStream/FileOutputStream
 * 		RandomAccessFile
 * 
 * 		网络IO：
 * 		Socket
 * 		ServerSocket
 * 		DatagrameSocket
 * 2. 在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 * 3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 * 
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 * 
 * 五、分散(Scatter)与聚集(Gather)
 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
 * 
 * 六、字符集：Charset
 * 编码：字符串 -> 字节数组
 * 解码：字节数组  -> 字符串
 * 
 * @author Tin
 *
 */
public class ChannelTest {
	
	// 字符集
	@Test
	public void test6() {
		
		Charset charset = Charset.forName("GBK");
		
		// 获取编码器
		CharsetEncoder ce = charset.newEncoder();
		// 获取解码器
		CharsetDecoder cd = charset.newDecoder();
		
		CharBuffer cBuf = CharBuffer.allocate(1024);
		cBuf.put("尚硅谷威武！");
		cBuf.flip();
		
		try {
			// 编码
			ByteBuffer bBuf = ce.encode(cBuf);
			for (int i = 0; i < bBuf.limit(); i++) {
				System.out.println(bBuf.get());
			}
			// 解码
			bBuf.flip();
			CharBuffer cBuf2 = cd.decode(bBuf);
			System.out.println(cBuf2.toString());
			
			Charset cs2 = Charset.forName("GBK");
			bBuf.flip();
			CharBuffer cBuf3 = cs2.decode(bBuf);
			System.out.println(cBuf3.toString());
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test5(){
		Map<String, Charset> map = Charset.availableCharsets();
		
		Set<Entry<String, Charset>> set = map.entrySet();
		
		for (Entry<String, Charset> entry : set) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
	}
	
	/**
	 * 分散与聚集
	 */
	@Test
	public void test4() {
		try {
			RandomAccessFile raf1 = new RandomAccessFile("D:/1.txt", "rw");
			
			// 获取通道
			FileChannel channel1 = raf1.getChannel();
			
			// 分配指定大小的缓冲区
			ByteBuffer buf1 = ByteBuffer.allocate(100);
			ByteBuffer buf2 = ByteBuffer.allocate(1024);
			
			ByteBuffer[] dsts = { buf1, buf2 };
			channel1.read(dsts);
			
			for (ByteBuffer buf : dsts) {
				buf.flip();
				System.out.println("--------------------------------------");
				System.out.println(new String(buf.array(), "utf-8"));
			}
			
			// 聚集写入
			RandomAccessFile raf2 = new RandomAccessFile("D:/2.txt", "rw");
			FileChannel channel2 = raf2.getChannel();
			channel2.write(dsts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通道之间的数据传输(直接缓冲区)
	 */
	@Test
	public void test3() {
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			long start = System.currentTimeMillis();
			
			inChannel = FileChannel.open(Paths.get("d:/1.avi"), StandardOpenOption.READ);
			outChannel = FileChannel.open(Paths.get("d:/2.avi"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
			
//			inChannel.transferTo(0, inChannel.size(), outChannel);
			outChannel.transferFrom(inChannel, 0, inChannel.size());
			
			long end = System.currentTimeMillis();
			System.out.println("耗费时间为：" + (end - start));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 利用直接缓冲区完成文件的复制（内存映射文件）
	 */
	@Test
	public void test2() {
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			long start = System.currentTimeMillis();
			
			inChannel = FileChannel.open(Paths.get("d:/1.avi"), StandardOpenOption.READ);
			outChannel = FileChannel.open(Paths.get("d:/2.avi"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
			
			//内存映射文件
			MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
			MappedByteBuffer outMappedBuf = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
			
			//直接对缓冲区进行数据的读写操作
			byte[] dst = new byte[inMappedBuf.limit()];
			inMappedBuf.get(dst);
			outMappedBuf.put(dst);
			
			inChannel.close();
			outChannel.close();
			
			long end = System.currentTimeMillis();
			System.out.println("耗费时间为：" + (end - start));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 利用通道完成文件复制（非直接缓冲区）
	 */
	@Test
	public void test1() {
		long st = System.currentTimeMillis();
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			fis = new FileInputStream("D:/1.mp4");
			fos = new FileOutputStream("D:/2.mp4");
			
			// 获取通道
			inChannel = fis.getChannel();
			outChannel = fos.getChannel();
			
			// 分配制定大小的缓冲区
			ByteBuffer buf = ByteBuffer.allocate(1024);
			
			// 将通道中的数据存入缓冲区中
			while (inChannel.read(buf) != -1) {
				buf.flip(); // 切换读取数据的模式
				// 将缓冲区中的数据写入通道中
				outChannel.write(buf);
				buf.clear();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ResourceUtil.closeResource(outChannel, inChannel, fis, fos);
		}
		long et = System.currentTimeMillis();
		System.out.println(et - st);
	}

}
