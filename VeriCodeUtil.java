import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;


public class VeriCodeUtil {
	public enum BaseMode{
		STRMODE,CHARMODE
	}
	//字符源模式
	private BaseMode baseMode = BaseMode.STRMODE;
	//验证码字符串数据源
	private final String STR_BASE = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
	//验证常用汉字字符集数据源
	private final String CHAR_BASE = 
		"\u7684\u4e00\u4e86\u662f\u6211\u4e0d\u5728\u4eba\u4eec\u6709\u6765\u4ed6\u8fd9" +
                "\u4e0a\u7740\u4e2a\u5730\u5230\u5927\u91cc\u8bf4\u5c31\u53bb\u5b50\u5f97\u4e5f\u548c" +
                "\u90a3\u8981\u4e0b\u770b\u5929\u65f6\u8fc7\u51fa\u5c0f\u4e48\u8d77\u4f60\u90fd\u628a" +
                "\u597d\u8fd8\u591a\u6ca1\u4e3a\u53c8\u53ef\u5bb6\u5b66\u53ea\u4ee5\u4e3b\u4f1a\u6837" +
                "\u5e74\u60f3\u751f\u540c\u8001\u4e2d\u5341\u4ece\u81ea\u9762\u524d\u5934\u9053\u5b83" +
                "\u540e\u7136\u8d70\u5f88\u50cf\u89c1\u4e24\u7528\u5979\u56fd\u52a8\u8fdb\u6210\u56de" +
                "\u4ec0\u8fb9\u4f5c\u5bf9\u5f00\u800c\u5df1\u4e9b\u73b0\u5c71\u6c11\u5019\u7ecf\u53d1" +
                "\u5de5\u5411\u4e8b\u547d\u7ed9\u957f\u6c34\u51e0\u4e49\u4e09\u58f0\u4e8e\u9ad8\u624b" +
                "\u77e5\u7406\u773c\u5fd7\u70b9\u5fc3\u6218\u4e8c\u95ee\u4f46\u8eab\u65b9\u5b9e\u5403" +
                "\u505a\u53eb\u5f53\u4f4f\u542c\u9769\u6253\u5462\u771f\u5168\u624d\u56db\u5df2\u6240" +
                "\u654c\u4e4b\u6700\u5149\u4ea7\u60c5\u8def\u5206\u603b\u6761\u767d\u8bdd\u4e1c\u5e2d" +
                "\u6b21\u4eb2\u5982\u88ab\u82b1\u53e3\u653e\u513f\u5e38\u6c14\u4e94\u7b2c\u4f7f\u5199" +
                "\u519b\u5427\u6587\u8fd0\u518d\u679c\u600e\u5b9a\u8bb8\u5feb\u660e\u884c\u56e0\u522b" +
                "\u98de\u5916\u6811\u7269\u6d3b\u90e8\u95e8\u65e0\u5f80\u8239\u671b\u65b0\u5e26\u961f" +
                "\u5148\u529b\u5b8c\u5374\u7ad9\u4ee3\u5458\u673a\u66f4\u4e5d\u60a8\u6bcf\u98ce\u7ea7" +
                "\u8ddf\u7b11\u554a\u5b69\u4e07\u5c11\u76f4\u610f\u591c\u6bd4\u9636\u8fde\u8f66\u91cd" +
                "\u4fbf\u6597\u9a6c\u54ea\u5316\u592a\u6307\u53d8\u793e\u4f3c\u58eb\u8005\u5e72\u77f3" +
                "\u6ee1\u65e5\u51b3\u767e\u539f\u62ff\u7fa4\u7a76\u5404\u516d\u672c\u601d\u89e3\u7acb" +
                "\u6cb3\u6751\u516b\u96be\u65e9\u8bba\u5417\u6839\u5171\u8ba9\u76f8\u7814\u4eca\u5176" +
                "\u4e66\u5750\u63a5\u5e94\u5173\u4fe1\u89c9\u6b65\u53cd\u5904\u8bb0\u5c06\u5343\u627e" +
                "\u4e89\u9886\u6216\u5e08\u7ed3\u5757\u8dd1\u8c01\u8349\u8d8a\u5b57\u52a0\u811a\u7d27" +
                "\u7231\u7b49\u4e60\u9635\u6015\u6708\u9752\u534a\u706b\u6cd5\u9898\u5efa\u8d76\u4f4d" +
                "\u5531\u6d77\u4e03\u5973\u4efb\u4ef6\u611f\u51c6\u5f20\u56e2\u5c4b\u79bb\u8272\u8138" +
                "\u7247\u79d1\u5012\u775b\u5229\u4e16\u521a\u4e14\u7531\u9001\u5207\u661f\u5bfc\u665a" +
                "\u8868\u591f\u6574\u8ba4\u54cd\u96ea\u6d41\u672a\u573a\u8be5\u5e76\u5e95\u6df1\u523b" +
                "\u5e73\u4f1f\u5fd9\u63d0\u786e\u8fd1\u4eae\u8f7b\u8bb2\u519c\u53e4\u9ed1\u544a\u754c" +
                "\u62c9\u540d\u5440\u571f\u6e05\u9633\u7167\u529e\u53f2\u6539\u5386\u8f6c\u753b\u9020" +
                "\u5634\u6b64\u6cbb\u5317\u5fc5\u670d\u96e8\u7a7f\u5185\u8bc6\u9a8c\u4f20\u4e1a\u83dc" +
                "\u722c\u7761\u5174\u5f62\u91cf\u54b1\u89c2\u82e6\u4f53\u4f17\u901a\u51b2\u5408\u7834" +
                "\u53cb\u5ea6\u672f\u996d\u516c\u65c1\u623f\u6781\u5357\u67aa\u8bfb\u6c99\u5c81\u7ebf" +
                "\u91ce\u575a\u7a7a\u6536\u7b97\u81f3\u653f\u57ce\u52b3\u843d\u94b1\u7279\u56f4\u5f1f" +
                "\u80dc\u6559\u70ed\u5c55\u5305\u6b4c\u7c7b\u6e10\u5f3a\u6570\u4e61\u547c\u6027\u97f3" +
                "\u7b54\u54e5\u9645\u65e7\u795e\u5ea7\u7ae0\u5e2e\u5566\u53d7\u7cfb\u4ee4\u8df3\u975e" +
                "\u4f55\u725b\u53d6\u5165\u5cb8\u6562\u6389\u5ffd\u79cd\u88c5\u9876\u6025\u6797\u505c" +
                "\u606f\u53e5\u533a\u8863\u822c\u62a5\u53f6\u538b\u6162\u53d4\u80cc\u7ec6";
	//验证码长度
	private final int VERIFICODE_SIZE= 4;
	//验证码字体
	private String[] fontFamily=new String[]{"Fixedsys"};
	//噪点数量
	private int noiseCount=5;
	//模糊线数量
	private int lineCount=5;
	//背景颜色
	private Color backgroundColor = Color.WHITE;
	
	public void setBaseMode(BaseMode baseMode) {
		this.baseMode = baseMode;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public void setNoiseCount(int noiseCount) {
		this.noiseCount = noiseCount;
	}
	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}
	private Random random = new Random();
	/**
	 * 生成验证码图片
	 * @param path
	 * @param width
	 * @param height
	 * @param verifiCodeSize
	 * @throws IOException
	 */
	public String createVerifiCodeImage(String path,int width,int height,int verifiCodeSize) throws IOException{
		createVerifiCodeImage(new FileOutputStream(path),STR_BASE,width,height,verifiCodeSize);
	}
	/**
	 * 生成验证码图片
	 * @param file
	 * @param width
	 * @param height
	 * @param verifiCodeSize
	 * @throws IOException
	 */
	public String createVerifiCodeImage(File file,int width,int height,int verifiCodeSize) throws IOException{
		createVerifiCodeImage(new FileOutputStream(file),STR_BASE,width,height,verifiCodeSize);
	}
	/**
	 * 生成验证码图片
	 * @param outputStream
	 * @param width
	 * @param height
	 * @param verifiCodeSize
	 * @throws IOException
	 */
	public String createVerifiCodeImage(OutputStream outputStream,int width,int height,int verifiCodeSize) throws IOException{
		createVerifiCodeImage(outputStream,STR_BASE,width,height,verifiCodeSize);
	}
	/**
	 * 生成验证码图片
	 * @param path
	 * @param strBase
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public String createVerifiCodeImage(String path,String strBase,int width,int height) throws IOException{
		createVerifiCodeImage(new FileOutputStream(path),strBase,width,height,VERIFICODE_SIZE);
	}
	/**
	 * 生成验证码图片
	 * @param file
	 * @param strBase
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public String createVerifiCodeImage(File file,String strBase,int width,int height) throws IOException{
		createVerifiCodeImage(new FileOutputStream(file),strBase,width,height,VERIFICODE_SIZE);
	}
	/**
	 * 生成验证码图片
	 * @param outputStream
	 * @param strBase
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public String createVerifiCodeImage(OutputStream outputStream,String strBase,int width,int height) throws IOException{
		createVerifiCodeImage(outputStream,strBase,width,height,VERIFICODE_SIZE);
	}
	/**
	 * 生成验证码图片
	 * @param path
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public String createVerifiCodeImage(String path,int width,int height) throws IOException{
		createVerifiCodeImage(new FileOutputStream(path),STR_BASE,width,height,VERIFICODE_SIZE);
	}
	/**
	 * 生成验证码图片
	 * @param file
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public String createVerifiCodeImage(File file,int width,int height) throws IOException{
		createVerifiCodeImage(new FileOutputStream(file),STR_BASE,width,height,VERIFICODE_SIZE);
	}
	/**
	 * 生成验证码图片
	 * @param outputStream
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public String createVerifiCodeImage(OutputStream outputStream,int width,int height) throws IOException{
		createVerifiCodeImage(outputStream,STR_BASE,width,height,VERIFICODE_SIZE);
	}
	/**
	 * 生成验证码图片
	 * @param path
	 * @param strBase
	 * @param width
	 * @param height
	 * @param verifiCodeSize
	 * @throws IOException
	 */
	public String createVerifiCodeImage(String path,String strBase,int width,int height,int verifiCodeSize) throws IOException{
		createVerifiCodeImage(new FileOutputStream(path),strBase,width,height,verifiCodeSize);
	}
	/**
	 * 生成验证码图片
	 * @param file
	 * @param strBase
	 * @param width
	 * @param height
	 * @param verifiCodeSize
	 * @throws IOException
	 */
	public String createVerifiCodeImage(File file,String strBase,int width,int height,int verifiCodeSize) throws IOException{
		createVerifiCodeImage(new FileOutputStream(file),strBase,width,height,verifiCodeSize);
	}
	/**
	 * 生成验证码图片
	 * @param output
	 * @param strBase
	 * @param width
	 * @param height
	 * @param verifiCodeSize
	 * @throws IOException
	 */
	public String createVerifiCodeImage(OutputStream output,String strBase,int width,int height,int verifiCodeSize) throws IOException{
		String verifiCode = null;
		switch(baseMode){
		case STRMODE : 
			verifiCode = getVerifiCodeFromStrBase(strBase,verifiCodeSize);
			break;
		case CHARMODE: 
			verifiCode = getVerifiCodeFromChinese(verifiCodeSize);
			break;
		}
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = bufferedImage.createGraphics();
		//绘制背景
		graphics2d.setColor(backgroundColor);
		graphics2d.fillRect(0, 0, width, height);
		//绘制噪点
		drawRGB(noiseCount,bufferedImage,width,height);
		//绘制模糊线
		drawLine(lineCount, graphics2d, width, height);
		//绘制验证码
		drawStr(verifiCode,graphics2d,width,height);
		//输出
		ImageIO.write(bufferedImage, "jpg", output);
		return verifiCode;
	}
	/**
	 * 绘制噪点
	 * @param Count
	 * @param bufferedImage
	 * @param width
	 * @param height
	 */
	private void drawRGB(int Count,BufferedImage bufferedImage,int width,int height){
		for(int i = 0; i < Count; i++){
			bufferedImage.setRGB(random.nextInt(width),random.nextInt(height), Integer.valueOf(randomColor().substring(4), 16));
		}
	}
	/**
	 * 绘制验证码
	 * @param str
	 * @param graphics2d
	 * @param width
	 * @param height
	 */
	private void drawStr(String str,Graphics2D graphics2d,int width,int height){
		int pos_x = 5;
		int space = width/str.length();
		for(int i=0;i<str.length();i++){
			graphics2d.setColor(new Color(Integer.valueOf(randomColor().substring(4), 16)));
			graphics2d.setFont(new Font(fontFamily[random.nextInt(fontFamily.length)], Font.ITALIC, height/3+random.nextInt(10)));
			graphics2d.drawString(String.valueOf(str.charAt(i)), pos_x, height/3+(int)(random.nextInt(height)/1.5));
			pos_x += space;
		}
	}
	/**
	 * 产生中文验证码
	 * @param verifiCodeSize
	 * @return
	 */
	private String getVerifiCodeFromChinese(int verifiCodeSize){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<verifiCodeSize;i++){
			sb.append(CHAR_BASE.charAt(random.nextInt(CHAR_BASE.length())));
		}
		return sb.toString();
	}
	/**
	 * 根据字符源产生验证码
	 * @param strBase
	 * @return
	 */
	private String getVerifiCodeFromStrBase(String strBase,int verifiCodeSize){
		if(strBase==null||strBase.length()==0)new IllegalArgumentException("字符源有误");
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<verifiCodeSize;i++){
			sb.append(strBase.charAt(random.nextInt(strBase.length())));
		}
		return sb.toString();
	}
	/**
	 * 生成随机颜色
	 * @return
	 */
	private String randomColor(){
		String red = Integer.toHexString(random.nextInt(225));
		String blue = Integer.toHexString(random.nextInt(225));
		String green = Integer.toHexString(random.nextInt(225));
		red = red.length()==1?"0"+red:red;
		blue = blue.length()==1?"0"+blue:blue;
		green = green.length()==1?"0"+green:green;
		return "0xFF"+red+blue+green;
	}
	/**
	 * 绘制模糊线
	 * @param count
	 * @param width
	 * @param height
	 */
	private void drawLine(int count,Graphics2D graphics2d,int width,int height){
		for(int i=0;i<count;i++){
			graphics2d.setColor(new Color(Integer.valueOf(randomColor().substring(4), 16)));
			graphics2d.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
		}
	}
}
