package org.chesno.glasometr.parse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.HttpHeaders;
import com.google.common.util.concurrent.RateLimiter;

public class ProtocolGrabberTest
{
	public static final String FILE_PREFFIX = "ns_golos_print_g_id_=";
	private static final Logger log = LoggerFactory.getLogger(ProtocolGrabberTest.class); 
	
	@Test
	public void Should() throws Exception
	{
		Random random = new Random();
		int i = 2700;
		RateLimiter limiter = RateLimiter.create(0.1);
		while (i <= 2700 && i > 0)
		{
			String fileName = FILE_PREFFIX+i;
			File file = new File(fileName);
			if (file.exists())
			{
				log.debug("{} - done already.", i);
				i--;
				continue;
			}
			
			HttpClient client = new DefaultHttpClient();
			limiter.acquire();
			HttpGet get = new HttpGet("http://w1.c1.rada.gov.ua/pls/radan_gs09/ns_golos_print?g_id="+i+"&vid=1");
			get.setHeader(HttpHeaders.ACCEPT_CHARSET, HTTP.UTF_8);
			HttpResponse response = client.execute(get);
			
			OutputStream os = new FileOutputStream(file);
			byte[] buff;
			int code = response.getStatusLine().getStatusCode();
			if (code != 200)
			{
				log.debug("{} - NOT({})", i, code);
				buff = new byte[0];
			}
			else
			{
				log.debug("{} - OK", i);
				Charset chrs = getCharset(response);
				InputStreamReader httpContent = new InputStreamReader(response.getEntity().getContent(), chrs);
				buff = IOUtils.toByteArray(httpContent);
				IOUtils.closeQuietly(httpContent);				
			}			
			IOUtils.write(buff, os);
			IOUtils.closeQuietly(os);
			i -= random.nextInt(30);
			response.getEntity();
		}
		
		log.debug("----");
	}

	private Charset getCharset(HttpResponse response)
	{
		String charsetHeader = response.getLastHeader("content-type").getValue();
		String indicator = "charset=";
		int startIdx = charsetHeader.indexOf(indicator);
		String charset = charsetHeader.substring(startIdx + indicator.length());
		Charset chrs = Charset.forName(charset);
		return chrs;
	}
}
