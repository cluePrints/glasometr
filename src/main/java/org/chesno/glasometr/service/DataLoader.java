package org.chesno.glasometr.service;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.chesno.glasometr.parse.JpaLoader;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DataLoader
{
	@PersistenceUnit(unitName = "derby")
	EntityManagerFactory ef = Persistence.createEntityManagerFactory("derby");

	private AtomicBoolean inProgress = new AtomicBoolean();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/load")
	public String load() throws Exception
	{
		if (inProgress.getAndSet(true))
		{
			return "Something already running";
		}

		try
		{
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
			
			return "-";

		} finally
		{
			inProgress.set(false);
		}
	}
}
