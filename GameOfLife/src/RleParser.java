import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RleParser{
	
	public boolean[][] runParser(String path){
		String strFile = file2string(path);
		String secondLine = getSecondLine(strFile);
		int x = getX(secondLine);
		int y = getY(secondLine);
		strFile=suppressF2L(strFile);
		String[] split = splitContent(strFile);
		boolean[][] res = makeTab(split, y, x);
		return res;
	}
	
	public String file2string(String path) {
		try {
			return Files.readString(Paths.get(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String suppressF2L(String str) {
		String res = "";
		int lineCounter =0;
		int beginIndex = 0;
		for (int i = 0; i < str.length(); i++) {
			if(lineCounter==2) break;
			beginIndex++;
			if(str.charAt(i)=='\n') lineCounter++;
		}
		//new string starts after first two lines and ends before '!' last char
		res=str.substring(beginIndex,str.length()-1);
		return res;
	}

	public String getSecondLine(String str) {
		String res ="";
		int lineCounter =0;
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i)=='\n') {
				lineCounter++;
				i++; // i goes on the next line
			}
			if(lineCounter==1) {
				res = res + str.charAt(i);
			}
			if(lineCounter>1) break;
		}
		return res;
	}

	public int getX(String str) {
		int res=0;
		String xString = "";
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i)=='x') {
				while(str.charAt(i+4)!=',') {
					xString = xString + String.valueOf(str.charAt(i+4));
					i++;
				}
				break;
			}
		}
		res=Integer.parseInt(xString);
		return res;
	}

	public int getY(String str) {
		int res=0;
		String yString = "";
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i)=='y') {
				while(str.charAt(i+4)!=',') {
					yString = yString + String.valueOf(str.charAt(i+4));
					i++;
				}
				break;
			}
		}
		res=Integer.parseInt(yString);
		return res;
	}

	public String[] splitContent(String str) {
		return str.replace("\n", "").split("[$]");
	}

	public boolean[] parseLine(String str) {
		List<Boolean> tmp = new ArrayList<Boolean>();
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i)=='b')
				tmp.add(false);
			else if(str.charAt(i)=='o')
				tmp.add(true);
			else {
				String strNumber = "";
				while(str.charAt(i)!='o' && str.charAt(i)!='b') {
					strNumber = strNumber + str.charAt(i);
					i++;
				}
				int number = Integer.parseInt(strNumber);
				while(number>0) {
					if(str.charAt(i)=='b')
						tmp.add(false);
					else
						tmp.add(true);
					number--;
				}
			}
		}
		boolean[] res = new boolean[tmp.size()];
		for(int i=0; i<res.length; i++) {
			res[i] = tmp.get(i);
		}
		return res;
	}
	
	public boolean[][] makeTab(String[] tab, int x, int y){
		boolean[][] res = new boolean [x][y];
		for (int i = 0; i < tab.length; i++) {
			boolean[] tmp = parseLine(tab[i]);
			//res[i]= tmp;
			for (int j = 0; j < res[i].length; j++) {
				if(j<tmp.length)
					res[i][j]=tmp[j];
				else
					res[i][j]=false;
			}
		}
		return res;
	}
}
