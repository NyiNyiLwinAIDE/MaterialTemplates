package nnl.aide.material.templates;

import android.content.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.v4.content.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;
import java.util.Map.*;
import java.util.zip.*;

public class $File
{
	public static final String contentDir = "ContentDir";

	public static String getDirAssets()
	{
		return "AssetsDir";
	}

	public static String getDirInternal()
	{
		return $App.context.getFilesDir().toString();
	}

	public static String getDirInternalCache()
	{
		File cd = $App.context.getCacheDir();
		if (cd == null)
			return getDirInternal();
		return cd.toString();
	}

	public static String getDirRootExternal()
	{
		return Environment.getExternalStorageDirectory().toString();
	}

	public static String getDirDefaultExternal()
	{
		File file = new File(Environment.getExternalStorageDirectory(), 
							 "/Android/data/" + $App.context.getPackageName() + "/files/");
		file.mkdirs();
		return file.toString();
	}

	public static boolean exists(String dir, String fileName)
	{
		if (dir != "AssetsDir")
			return new File(dir, fileName).exists();

		try
		{
			return 
				(Arrays.asList($App.context.getAssets().list("")).indexOf(fileName) > -1);
		}
		catch (IOException e)
		{return false;}
	}

	public static boolean delete(String dir, String fileName)
	{
		return new File(dir, fileName).delete();
	}

	public static void makeDir(String parent, String dir)
	{
		File file = new File(parent, dir);
		file.mkdirs();
	}

	public  long size(String dir, String fileName)
	{
		return new File(dir, fileName).length();
	}

	public  long lastModified(String dir, String fileName)
	{
		return new File(dir, fileName).lastModified();
	}

	public static boolean isDirectory(String dir, String fileName)
	{
		return new File(dir, fileName).isDirectory();
	}

	public static String combine(String dir, String fileName)
	{
		return new File(dir, fileName).toString();
	}

	public void deleteRecursive(File fileOrDirectory)
	{
		if (fileOrDirectory.isDirectory())
		{
			for (File child : fileOrDirectory.listFiles())
			{
				deleteRecursive(child);
			}
		}
		fileOrDirectory.delete();
	}

	public void copyDirectory(File sourceLocation , File targetLocation)
	{
		try
		{
			if (sourceLocation.isDirectory())
			{
				if (!targetLocation.exists())
				{
					targetLocation.mkdirs();
				}
				String[] children = sourceLocation.list();
				for (int i=0; i < children.length; i++)
				{
					copyDirectory(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
				}
			}
			else
			{ // make sure the directory we plan to store the recording in exists
				File directory = targetLocation.getParentFile();
				directory.mkdirs();
				InputStream in = new FileInputStream(sourceLocation);
				OutputStream out = new FileOutputStream(targetLocation); // Copy the bits from instream to outstream
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0)
				{
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

	public static String[] listFiles(String dir)
	{
		if (dir != "AssetsDir")
		{
			File folder = new File(dir);
			if (folder.isDirectory())
				return folder.list();
			else return null;
		}
		else
		{
			try
			{
				return $App.context.getAssets().list("");
			}
			catch (IOException e)
			{return null;}
		}
	}

	public static boolean getExternalWritable()
	{
		String state = Environment.getExternalStorageState();

		return ("mounted".equals(state));
	}

	public static boolean getExternalReadable()
	{
		String state = Environment.getExternalStorageState();

		return (("mounted".equals(state)) || ("mounted_ro".equals(state)));
	}

	public static InputStream openInput(String dir, String fileName)

	{
		InputStream is=null;
		try
		{
			if (dir == "AssetsDir")
			{
				is = $App.context.getAssets().open(fileName);

			}
			else if (dir == "ContentDir")
			{
				is = $App.context.getContentResolver().openInputStream(Uri.parse(fileName));
			}
			else
				is =
					new BufferedInputStream(new FileInputStream(new File(dir, fileName)), 
											4096);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return is;
	}

	public static String readString(String dir, String fileName)
	{
		InputStream in = openInput(dir, fileName);
		TextReader tr = new TextReader(in);
		return tr.readAll();
	}

	public static List readList2(String dir, String fileName)
	{
		InputStream in = openInput(dir, fileName);
		TextReader tr = new TextReader(in);
		return tr.readList();
	}

	public static void writeList(String dir, String fileName, List list)
	{
		OutputStream out = openOutput(dir, fileName, false);
		TextWriter tw = new TextWriter(out);
		tw.writeList(list);
		tw.close();
	}

	public static void writeString(String dir, String fileName, String text)
	{
		OutputStream out = openOutput(dir, fileName, false);
		TextWriter tw = new TextWriter(out);
		tw.write(text);
		tw.close();
	}

	public static void writeMap(String dir, String fileName, Map<Object,Object> m)
	{
		try
		{
			OutputStream out = openOutput(dir, fileName, false);
			Properties p = new Properties();
			/*
			 for (Iterator localIterator = m.entrySet().iterator(); localIterator.hasNext(); ) {
			 Entry<Object,Object> e = (Entry<Object, Object>) localIterator.next();
			 p.setProperty(String.valueOf(e.getKey()),String.valueOf(e.getValue()));
			 }
			 */
			for (Map.Entry<Object,Object> entry : m.entrySet())
			{
				p.setProperty(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
			}

			p.store(out, null);
			out.close();
		}
		catch (Exception e)
		{}
	}

	public static Map readMap2(String dir, String fileName)
	{
		return readMap(dir, fileName, null);
	}

	public static Map readMap(String dir, String fileName, Map map)
	{
		try
		{
			InputStream in = openInput(dir, fileName);
			Properties p = new Properties();
			p.load(in);
			if (map == null)
				map = new HashMap();

			for (Iterator<?> localIterator = p.entrySet().iterator(); localIterator.hasNext();)
			{ 
				Entry<Object,Object> e = (Entry<Object, Object>)localIterator.next();
				map.put(e.getKey(), e.getValue());
			}

			in.close();
			return map;
		}
		catch (IOException e)
		{return null;}
	}

	public static void copy(String dirSource, String fileSource, String dirTarget, String fileTarget)
	{
		try
		{
			delete(dirTarget, fileTarget);
			InputStream in = openInput(dirSource, fileSource);
			OutputStream out = openOutput(dirTarget, fileTarget, false);
			copy(in, out);
			in.close();
			out.close();
		}
		catch (IOException e)
		{}
	}

	public static void copy(InputStream in, OutputStream out)
	{
		try
		{
			byte[] buffer = new byte[8192];
			int count = 0;
			while ((count = in.read(buffer)) > 0)
				out.write(buffer, 0, count);

			in.close();
		}
		catch (IOException e)
		{}
	}


	public static OutputStream openOutput(String dir, String fileName, boolean append)
	{
		OutputStream o = null;
		try
		{
			o = new FileOutputStream(new File(dir, fileName), append);
		}
		catch (IOException e)
		{return null;}
		return o;
	}

	//ByteReader
	//===============================================================
	public static class ByteReader
	{
		private InputStream is;

		public ByteReader(InputStream is)
		{
			this.is = is;
		}

		public void initializeFromBytesArray(byte[] buffer, int startOffset, int maxCount)
		{
			is = new ByteArrayInputStream(buffer, startOffset, maxCount);
		}

		public void close()
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{}
		}

		public int readBytes(byte[] buffer, int startOffset, int maxCount)
		{
			try
			{
				return is.read(buffer, startOffset, maxCount);
			}
			catch (IOException e)
			{return -1;}
		}

		public int bytesAvailable()
		{
			try
			{
				return is.available();
			}
			catch (IOException e)
			{return -1;}
		}
	}



	//ByteWriter
	//=========================================================================================
	public static class ByteWriter
	{
		private OutputStream os;

		public ByteWriter(OutputStream os)
		{
			this.os = os;
		}


		public void initializeToBytesArray(int startSize)
		{
			os = new ByteArrayOutputStream(startSize);
		}

		/*
		 public byte[] toBytesArray()
		 {
		 //if ((getObject() instanceof ByteArrayOutputStream))
		 //throw new RuntimeException("ToBytes can only be called after InitializeToBytesArray.");
		 return os.toByteArray();

		 else
		 return null;
		 }
		 */

		public void close()
		{
			try
			{
				os.close();
			}
			catch (IOException e)
			{}
		}

		public void flush()
		{
			try
			{
				os.flush();
			}
			catch (IOException e)
			{}
		}

		public void writeBytes(byte[] buffer, int startOffset, int length)
		{
			try
			{
				os.write(buffer, startOffset, length);
			}
			catch (IOException e)
			{}
		}
	}

	//"TextReader"
	//===========================================================================================
	public static class TextReader extends BufferedReader
	{
		public TextReader(InputStream InputStream)
		{
			super(new BufferedReader(new InputStreamReader(InputStream, Charset.forName("UTF8")), 
									 4096));
		}

		public TextReader(InputStream InputStream, String Encoding)
		{
			super(new BufferedReader(new InputStreamReader(InputStream, Charset.forName(Encoding)),
									 4096));
		}

		public String readLine()
		{
			try
			{
				return super.readLine();
			}
			catch (IOException e)
			{return null;}
		}

		public int read(char[] Buffer, int StartOffset, int Length)
		{
			try
			{
				return super.read(Buffer, StartOffset, Length);
			}
			catch (IOException e)
			{return -1;}
		}

		public boolean ready()
		{
			try
			{
				return super.ready();
			}
			catch (IOException e)
			{return false;}
		}

		public String readAll()
		{
			int count;
			char[] buffer = new char[1024];
			StringBuilder sb = new StringBuilder(1024);

			while ((count = read(buffer, 0, buffer.length)) != -1)
				if (count < buffer.length)
					sb.append(new String(buffer, 0, count));
				else
					sb.append(buffer);

			close();
			return sb.toString();
		}

		public List readList()
		{
			String line;
			List<String> List = new ArrayList<String>();

			while ((line = readLine()) != null)
				List.add(line);

			close();
			return List;
		}

		public long skip(int NumberOfCharaceters)
		{
			try
			{
				return super.skip(NumberOfCharaceters);
			}
			catch (IOException e)
			{return -1;}
		}

		public void close()
		{
			try
			{
				super.close();
			}
			catch (IOException e)
			{}
		}
	}

	//TextWriter
	//======================================================================================
	public static class TextWriter extends BufferedWriter
	{
		public TextWriter(OutputStream OutputStream)
		{
			super(
				new BufferedWriter(new OutputStreamWriter(OutputStream, Charset.forName("UTF8")), 
								   4096));
		}

		public TextWriter(OutputStream OutputStream, String Encoding)
		{
			super(
				new BufferedWriter(new OutputStreamWriter(OutputStream, Charset.forName(Encoding)), 
								   4096));
		}

		public void write(String Text)
		{
			try
			{
				super.write(Text);
			}
			catch (IOException e)
			{}
		}

		public void writeLine(String Text)
		{
			try
			{
				write(Text + "\n");
			}
			catch (Exception e)
			{}
		}

		public void writeList(List list)
		{
			for (Iterator localIterator =list.iterator(); localIterator.hasNext();)
			{ Object line = localIterator.next();
				writeLine(String.valueOf(line));
			}
		}

		public void close()
		{
			try
			{
				super.close();
			}
			catch (IOException e)
			{}
		}

		public void flush()
		{
			try
			{
				super.flush();
			}
			catch (IOException e)
			{}
		}
	}

	//========
	//Zip Unzip
	//========

	private static String SOURCE_FOLDER = "";

    public static boolean zipDirectory(String src, String dest, boolean includeRoot)
	{
        SOURCE_FOLDER = new File(src).getAbsolutePath().toString();
		if (includeRoot)
		{
			SOURCE_FOLDER = SOURCE_FOLDER.substring(0, SOURCE_FOLDER.lastIndexOf("/"));
		}
        try
		{
            OutputStream fileOutputStream = new FileOutputStream(dest);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            File file = new File(src);
            boolean addDirectory = addDirectory(zipOutputStream, file);
            zipOutputStream.close();
            System.out.println("Zip file has been created!");
            return addDirectory;
        }
		catch (IOException e)
		{
            System.out.println(e.toString());
            return false;
        }
    }

    public static boolean zipFile(String dir, String fileName, String dest)
	{
        try
		{
            byte[] bArr = new byte[1024];
            OutputStream fileOutputStream = new FileOutputStream(dest);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            FileInputStream fileInputStream = new FileInputStream(new File(dir, fileName));
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOutputStream.putNextEntry(zipEntry);
			int read = fileInputStream.read(bArr);
			while (read > 0)
			{
				zipOutputStream.write(bArr, 0, read);
            }
			zipOutputStream.closeEntry();
			fileInputStream.close();
			zipOutputStream.close();
			System.out.println("Zip file has been created!");
			return true;
        }
		catch (IOException e)
		{
            System.out.println(e.toString());
            return false;
        }
    }

    public static boolean unzip(String src, String dest)
	{
        if (src == null || src.equals(""))
		{
            System.out.println("Invalid source file");
            return false;
        }
        System.out.println("Zip file extracted!");
        return unzip2(src, dest);
    }

    private static boolean unzip2(String src, String dest)
	{
        try
		{
            new File(dest).mkdir();
           	System.out.println(dest + " created.");

            ZipFile zipFile = new ZipFile(src);

            Enumeration entries = zipFile.entries();
            while (entries.hasMoreElements())
			{
                ZipEntry zipEntry = (ZipEntry) entries.nextElement();

                File file = new File(dest, zipEntry.getName());

                file.getParentFile().mkdirs();
                if (!zipEntry.isDirectory())
				{
                    System.out.println("Extracting " + file.toString());

                    BufferedInputStream bufferedInputStream = new BufferedInputStream(zipFile.getInputStream(zipEntry));

                    byte[] bArr = new byte[1024];

                    OutputStream fileOutputStream = new FileOutputStream(file);

                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 1024);
					int read = bufferedInputStream.read(bArr, 0, 1024);

                    while (read > 0)
					{
                        bufferedOutputStream.write(bArr, 0, read);
                    }
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                    bufferedInputStream.close();
                }
            }
            return true;
        }
		catch (IOException e)
		{
            System.out.println(e.toString());
            return false;
        }
    }

    private static String generateZipEntry(String str)
	{
		return str.substring(SOURCE_FOLDER.length() + 1, str.length());
    }

    private static boolean addDirectory(ZipOutputStream zipOutputStream, File file)
	{

        File[] listFiles = file.listFiles();
        System.out.println("Adding directory " + file.getName().toString());
        for (int i = 0; i < listFiles.length; i++)
		{
            if (!listFiles[i].isDirectory())
			{
                try
				{
                    System.out.println("Adding file " + listFiles[i].getAbsolutePath());
                    byte[] bArr = new byte[1024];
                    FileInputStream fileInputStream = new FileInputStream(listFiles[i].getAbsoluteFile());
                    ZipEntry zipEntry;

					zipEntry = new ZipEntry(generateZipEntry(listFiles[i].getAbsoluteFile().toString()));
					zipOutputStream.putNextEntry(zipEntry);

					int read = fileInputStream.read(bArr);
                    while (read > 0)
					{
                        zipOutputStream.write(bArr, 0, read);
						read = fileInputStream.read(bArr);
                    }
                    zipOutputStream.closeEntry();
                    fileInputStream.close();
                }
				catch (IOException e)
				{
                    System.out.println(e.toString());
                    return false;
                }
            }
			else
			{
				if (!addDirectory(zipOutputStream, listFiles[i])) return false;
            }
        }
        return true;
    }

	public static void writeObject(String dir, String filename, Object obj)
	{
		try
		{
			OutputStream os=openOutput(dir, filename, false);
			ObjectOutputStream objos=new ObjectOutputStream(os);
			objos.writeObject(obj);
			os.close();
			objos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static Object readObject(String dir, String filename)
	{
		try
		{
			InputStream is=openInput(dir, filename);
			ObjectInputStream objis=new ObjectInputStream(is);
			Object obj=objis.readObject();
			is.close();
			objis.close();
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List readList(String dir, String filename)
	{
		return (List)readObject(dir, filename);
	}

	public static Map readMap(String dir, String filename)
	{
		return (Map)readObject(dir, filename);
	}

	public static Uri getUri(String dir, String filename)
	{
		Uri uri=null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
		{
			uri = FileProvider.getUriForFile($App.context, $App.context.getPackageName()+".provider", new File(dir, filename));
		}
		else
		{
			uri = Uri.parse("file://" + $File.combine(dir, filename));
		}

		return uri;
	}

	public static void installApk(String dir, String filename)
	{
		Uri uri=getUri(dir, filename);
		installApk(uri);
	}

	public static void installApk(Uri uri)
	{
		try
		{
			Intent intent=new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(uri, "application/vnd.android.package-archive");
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			$App.context.startActivity(intent);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String getRealPathFromURI(Uri contentUri)
	{
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = $App.context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst())
		{
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
	
	public static void share(String dir,String filename,String mime){
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		Uri uri = getUri(dir,filename);
		sharingIntent.setType(mime);
		sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
		
		$App.context.startActivity(Intent.createChooser(sharingIntent, "Share"));

	}

}


