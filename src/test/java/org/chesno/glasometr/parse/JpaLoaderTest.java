package org.chesno.glasometr.parse;

import java.io.File;
import java.io.FileFilter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;


public class JpaLoaderTest
{
	@Test
	public void test() throws Exception
	{
		EntityManagerFactory ef = Persistence.createEntityManagerFactory("derby");
//		new JpaLoader(ef).load(new File("./ns_golos_print_g_id_=2700"));
		File root = new File(".");

		File[] files = root.listFiles(new FileFilter()
		{
			public boolean accept(File pathname)
			{
				return pathname.getName().startsWith("ns_golos_print")
						&& pathname.length() > 10;
			}
		});
		new JpaLoader(ef).load(files);
	}
}
