package testdata_gen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.io.*;
//34180198
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
public class DataGen {
	public static void main(String[] args) throws SQLException,IOException
	{
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
			connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/benchmark1",
					"benchmark","benchmark");
			
		}
		catch(SQLException e)
		{
			System.out.println("Connection error");
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String st = br.readLine();
		int N = Integer.parseInt(st);
		String insertA = "insert into A"+N+"(A_ID,A_NAME,A_DETAILS,C_KEY) VALUES (?,?,?,?)";
		String insertB = "insert into B"+N+"(B_ID,B_NAME,B_DETAILS,A_KEY) VALUES (?,?,?,?)";
		String insertC = "insert into C"+N+"(C_ID,C_NAME,C_DETAILS,B_KEY) VALUES (?,?,?,?)";
		
		//delete all data from tables A,b and C
		
		PreparedStatement pa = connection.prepareStatement("delete from A"+N);
		pa.execute();
		pa = connection.prepareStatement("delete from B"+N);
		pa.execute();
		pa = connection.prepareStatement("delete from C"+N);
		pa.execute();
		
		//first some formality
		PreparedStatement ppta = connection.prepareStatement(insertA);
		PreparedStatement pptb = connection.prepareStatement(insertB);
		PreparedStatement pptc = connection.prepareStatement(insertC);
		
		RandomString a= new RandomString(15);
		
		ppta.setInt(1, 1);
		ppta.setString(2,a.nextString());
		ppta.setString(3,a.nextString());
		ppta.setNull(4, java.sql.Types.INTEGER);
		ppta.executeUpdate();
		
		ppta.setInt(1, 2);
		ppta.setString(2,a.nextString());
		ppta.setString(3,a.nextString());
		ppta.setNull(4, java.sql.Types.INTEGER);
		ppta.executeUpdate();

		//2nd
		pptb.setInt(1,1);
		pptb.setString(2,a.nextString());
		pptb.setString(3,a.nextString());
		pptb.setNull(4,java.sql.Types.INTEGER);
		pptb.executeUpdate();
		
		//3rd
		
		pptc.setInt(1,1);
		pptc.setString(2,a.nextString());
		pptc.setString(3,a.nextString());
		pptc.setNull(4,java.sql.Types.INTEGER);
		
		pptc.executeUpdate();
		
		for(int i=2;i <= N;i++)
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
			
			if(i > 2)
			{
				ppta.setInt(1, i);
				ppta.setString(2,a.nextString());
				ppta.setString(3,a.nextString());
				ppta.setInt(4, i-1);
				ppta.executeUpdate();
			}
			System.out.println(i);
		}
		
	}

}
