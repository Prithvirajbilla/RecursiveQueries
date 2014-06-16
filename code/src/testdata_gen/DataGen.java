import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 */

/**
 * @author pbilla
 *
 */
class InsertionThread implements Runnable
{
	String tb;
	long f, t;
/*	String CONNECTION_URL ="jdbc:oracle:thin:@//localhost:1521/benchmark1";
	String USER_NAME = "benchmark";
	String PASS = "benchmark";
*/	
	static String CONNECTION_URL = "jdbc:oracle:thin:@//infa.cx6pxciausrl.ap-southeast-1.rds.amazonaws.com:1521/ORCL";
	static String USER_NAME = "prithvirajbilla";
	static String PASS ="Lathamadhukar7";
	
	long N;
	public InsertionThread(long fron,long to,long n)
	{
		f = fron;
		t = to;
		N = n;
	}
	public void run()
	{
		/**
		 * Connect to the server
		 */
		String insertA = "insert into A"+N+"(A_ID,A_NAME,A_DETAILS,C_KEY) VALUES (?,?,?,?)";
		String insertB = "insert into B"+N+"(B_ID,B_NAME,B_DETAILS,A_KEY) VALUES (?,?,?,?)";
		String insertC = "insert into C"+N+"(C_ID,C_NAME,C_DETAILS,B_KEY) VALUES (?,?,?,?)";

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("JDBC driver not found");
		}
		Connection connection = null;
		try
		{
			connection = DriverManager.getConnection(CONNECTION_URL,
					USER_NAME,PASS);
			
		}
		catch(SQLException e)
		{
			System.out.println("Connection error");
			e.printStackTrace();
		}
		//first some formality
		PreparedStatement ppta = null,pptb = null,pptc = null;
		try {
			 ppta = connection.prepareStatement(insertA);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			 pptb = connection.prepareStatement(insertB);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			 pptc = connection.prepareStatement(insertC);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RandomString a= new RandomString(15);
		try
		{
			ppta.setInt(1, (int) f);
			ppta.setString(2,a.nextString());
			ppta.setString(3,a.nextString());
			ppta.setNull(4, java.sql.Types.INTEGER);
			ppta.executeUpdate();
		
			ppta.setInt(1, (int) (f+1));
			ppta.setString(2,a.nextString());
			ppta.setString(3,a.nextString());
			ppta.setNull(4, java.sql.Types.INTEGER);
			ppta.executeUpdate();

			//2nd
			pptb.setInt(1,(int) f);
			pptb.setString(2,a.nextString());
			pptb.setString(3,a.nextString());
			pptb.setNull(4,java.sql.Types.INTEGER);
			pptb.executeUpdate();
			
			//3rd
			
			pptc.setInt(1,(int) f);
			pptc.setString(2,a.nextString());
			pptc.setString(3,a.nextString());
			pptc.setNull(4,java.sql.Types.INTEGER);
			
			pptc.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		for(int i=(int) f+1;i <= t;i++)
		{
			try
			{
				pptc.setInt(1,i);
				pptc.setString(2,a.nextString());
				pptc.setString(3,a.nextString());
				pptc.setInt(4,i-1);
				pptc.executeUpdate();
	
				pptb.setInt(1,i);
				pptb.setString(2,a.nextString());
				pptb.setString(3,a.nextString());
				pptb.setInt(4,i-1);
				pptb.executeUpdate();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			if(i > (int) f+1)
			{
				try
				{
					ppta.setInt(1, i);
					ppta.setString(2,a.nextString());
					ppta.setString(3,a.nextString());
					ppta.setInt(4, i-1);
					ppta.executeUpdate();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			if(i%(N/10) == 0)
			System.out.println(i);
		}
	}
}

class RandomString {

	  private static char[] symbols;

	  static {
	    StringBuilder tmp = new StringBuilder();
	    for (char ch = '0'; ch <= '9'; ++ch)
	      tmp.append(ch);
	    for (char ch = 'a'; ch <= 'z'; ++ch)
	      tmp.append(ch);
	    symbols = tmp.toString().toCharArray();
	  }   

	  private final Random random = new Random();

	  private final char[] buf;

	  public RandomString(int length) {
	    if (length < 1)
	      throw new IllegalArgumentException("length < 1: " + length);
	    buf = new char[length];
	  }

	  public String nextString() {
	    for (int idx = 0; idx < buf.length; ++idx) 
	      buf[idx] = symbols[random.nextInt(symbols.length)];
	    return new String(buf);
	  }
	}

public class datagen {

	/**
	 * @param args
	 */
/*	static String CONNECTION_URL = "jdbc:oracle:thin:@//localhost:1521/benchmark1";
	static String USER_NAME = "benchmark";
	static String PASS = "benchmark";
*/	
	static String CONNECTION_URL = "jdbc:oracle:thin:@//infa.cx6pxciausrl.ap-southeast-1.rds.amazonaws.com:1521/ORCL";
	static String USER_NAME = "prithvirajbilla";
	static String PASS ="Lathamadhukar7";
	private static PreparedStatement ua;
	private static PreparedStatement ub;
	private static PreparedStatement uc;
	public static void main(String[] args) throws IOException, SQLException, InterruptedException {
		/**
		 * Connect to the server
		 */
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("JDBC driver not found");
		}
		Connection connection = null;
		try
		{
			connection = DriverManager.getConnection(CONNECTION_URL,
					USER_NAME,PASS);
			
		}
		catch(SQLException e)
		{
			System.out.println("Connection error");
			e.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String inputLine = null;
		System.out.println("Number of records to create in the database:");
		long   N;
		inputLine = br.readLine();
		N = Integer.parseInt(inputLine);
		System.out.println("Number of threads need to create the database:");
		int num_threads;
		inputLine = br.readLine();
		num_threads = Integer.parseInt(inputLine);
		
		//deleting all the previous records;
		String tA = "A"+N;
		String tB = "B"+N;
		String tC = "C"+N;
		
		PreparedStatement pa = connection.prepareStatement("drop table "+tA+" cascade constraints");
		//pa.execute();
		pa = connection.prepareStatement("drop table "+tB+" cascade constraints");
		//pa.execute();
		pa = connection.prepareStatement("drop table "+tC+" cascade constraints");
		//pa.execute();
		//creating the tables for the awesome 
		
		
		//Threading for the god sake,All men must die
		ExecutorService es = Executors.newCachedThreadPool();
		long vi = N/num_threads;
		for(int i =0; i< num_threads; i++)
		{
			es.execute(new InsertionThread((i*vi)+1,(i+1)*vi,N));
		}
		//now i have to update few columns for the awesomeness sake
		
		String updateA = "Update "+tA+" set C_KEY=? where A_ID=?";
		String updateB = "Update "+tB+" set A_KEY=? where B_ID=?";
		String updateC = "update "+tC+" set B_KEY=? where C_ID=?";
		es.shutdown();
		boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
		ua = connection.prepareStatement(updateA);
		ub = connection.prepareStatement(updateB);
		uc = connection.prepareStatement(updateC);
		
		if(finished)
		{	
			for(int i =1; i <= num_threads;i++)
			{
				int f = (int) ((vi*i) + 1);
				System.out.println(f);
				ua.setInt(1, (int) f-1);
				ua.setInt(2,(int)f);
				ua.executeUpdate();
			
				ua.setInt(1, (int) f);
				ua.setInt(2, (int)f+1);
				ua.executeUpdate();
	
				//2nd
				ub.setInt(1,(int) f-1);
				ub.setInt(2,(int)f);
				ub.executeUpdate();
				
				//3rd
				
				uc.setInt(1,(int) f-1);
				uc.setInt(2,(int)f);
				uc.executeUpdate();
		
			}
		}

	}

}
