import static java.util.stream.Collectors.toMap;
import java.util.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Kriptoanaliz {static String wayForRezult="C:\\file1.txt";
	private static final String STABC ="абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\":-!? 1234567890";

	private static final char[] ABC = {'а','б','в','г','д','е','ё','ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х','ц','ч','ш','щ','ъ','ы','ь','э','ю','я','.',',','"',':','-','!','?',' ','1','2','3','4','5','6','7','8','9','0'};
	
	//метод получения корректного ключа из введенной пользователем строки
	public static int getKey(String kluchs) {
		if(kluchs.contains("--")) {kluchs=kluchs.replace ("--", "");}
		int keyForShifr=0;
				try
	    {	  keyForShifr = Integer.parseInt(kluchs.trim());
			if(keyForShifr>STABC.length())   {    keyForShifr = keyForShifr%ABC.length;
	    }else if(keyForShifr<0) {
	    	if(Math.abs(keyForShifr)>STABC.length()) {keyForShifr = keyForShifr%ABC.length;}
	    	keyForShifr=STABC.length()+keyForShifr;
	    }
	     
	      //System.out.println("Ключ "+keyForShifr+" "+STABC.length());
	    }
	    catch (NumberFormatException nfe)
	    {
	    	System.out.println("Некорректный ключ!");
	    }
		return keyForShifr;
	}
	//метод шифрования/дешифрования с использованием ключа
	public static String kodDekod(String str_for_works, int kaysik) {
		String rezults="";
		char[] charTextForShifr = str_for_works.toCharArray();
			      for(int i=0;i<charTextForShifr.length;i++) {
	    	  if(STABC.indexOf(charTextForShifr[i])>=0) {
	    		 // System.out.println("нашел такой символ"+i);
	    		  if(STABC.indexOf(charTextForShifr[i])+kaysik>=STABC.length()) {
	    			  rezults=rezults+ABC[STABC.indexOf(charTextForShifr[i])+kaysik-STABC.length()];//System.out.println("Вышел за алфавит, закругляюсь"+i+": "+charTextForShifr[i]+": "+rezults);
	    			  }	    		      	  
	    	  else {rezults=rezults+ABC[STABC.indexOf(charTextForShifr[i])+kaysik];//System.out.println("Попал в алфавит "+i+": "+charTextForShifr[i]+": "+rezults);
	    	  }    } 
	    	  if(STABC.indexOf(charTextForShifr[i])==-1) {
	    		  rezults=rezults+charTextForShifr[i];    }
	} return rezults;}
	//старый метод проверки на длину слова
	/*public static boolean checkWhiteSpaces(String strForProv) {int poz=0; boolean par=true;
		
		for(int i=poz;i<strForProv.length();) {
			if(strForProv.indexOf(' ',poz)-poz>18||poz==0&&strForProv.indexOf(' ',poz)<=0||strForProv.indexOf(' ',poz)-poz==0||strForProv.length()-poz>20&&strForProv.indexOf(' ',poz)<=0) {par= false;break;}
			if(strForProv.indexOf(' ',poz)==-1) {break;}
			poz=strForProv.indexOf(' ',poz)+1;	}
		
		return par;
	}
	*/
	//проверка строки на длину слов
	public static boolean checkWhiteSpaces(String strForProv) {int poz=0; boolean par=true;
	String[] masWord=strForProv.split(" ");
	for(int i=0;i<masWord.length;i++) {
		if(masWord.length<2) {par= false;break;}else {
		if(masWord[i].length()>24) {
			poz++;	
			if(poz*100/masWord.length>10) {
			par= false;//System.out.println("Длинное слово "+(poz*100/masWord.length));
			break;}}
		
		}

		}
	
	return par;
}
	//проверка на наличие пробела после знаков пунктуации
	public static boolean punctuationMarks(String strForProv1) {boolean par1=true; int countFals=0;int countMarks=1;
	char [] strForProvInChar=strForProv1.toCharArray();
	for(int i=0;i<strForProv1.length()-1;i++) {
		if(strForProvInChar[i]=='.'||strForProvInChar[i]==','||strForProvInChar[i]==';'||strForProvInChar[i]==':'||strForProvInChar[i]=='!'||strForProvInChar[i]=='?'){countMarks++;
		if((strForProvInChar[i]=='.'||strForProvInChar[i]==','||strForProvInChar[i]==';'||strForProvInChar[i]==':'||strForProvInChar[i]=='!'||strForProvInChar[i]=='?')&&strForProvInChar[i+1]!=' '){
			countFals++;			
		}
	
		}}	if(countFals*100/countMarks>10) { par1=false; //System.out.println("Нет пробелов "+countFals+" i "+countMarks);
		}	
	return par1;}
			   
	//проверка на количество символов между гласными
	public static boolean countLit(String strForProv2) {boolean par2=true; String glas="аоуеыиэяю"; int pozGl=0; int countMark=0;
	char [] strForProvInChar2=strForProv2.toCharArray();
	for(int i=0;i<strForProvInChar2.length-1;i++) {  
		if(i-pozGl>18){countMark++;
		if(countMark*100/strForProvInChar2.length>10) {
			par2=false;//System.out.println("Много согласных"+countMark*100/strForProvInChar2.length);
		break;}}
		
		if(glas.indexOf(strForProvInChar2[i])!=-1) {pozGl=i;}
			}
		return par2;}
	//статистический анализ строки
	public static HashMap ststAnaliz(String str_for_analiz) {char [] char_str_for_analiz=str_for_analiz.toCharArray(); double count; double procentLit=0;
		HashMap<Character, Double> mapStAnStr = new HashMap<>();
		for(int i=0;i<ABC.length;i++)
		{count=0;
			for(int j=0;j<str_for_analiz.length();j++) {
				if(char_str_for_analiz[j]==ABC[i]) {count++;
					
				} procentLit= count*10000/str_for_analiz.length();  mapStAnStr.put(ABC[i],procentLit );
			}
		}
		return sortedMap(mapStAnStr);
	}
	//сортировка карты
	public static HashMap sortedMap(HashMap<Character, Double> mapForSorted) {
		HashMap<Character, Double>  sorted = mapForSorted.entrySet()
            .stream()
            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .collect(
                toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                    LinkedHashMap::new));            
        return sorted;
}
	//сопоставление ключей двух отсортированых карт
	public static HashMap comparisonMap(HashMap<Character, Double> mapForcomparison1,HashMap<Character, Double> mapForcomparison2) {
		HashMap<Character, Character> mapComparison = new HashMap<>();
		   Character[] keysMapForcomparison1 = mapForcomparison1.keySet().toArray(new Character[0]);
	      // System.out.println("Ключи зашифрованой строки: " + keysMapForcomparison1);
	       Character[] keysMapForcomparison2 = mapForcomparison2.keySet().toArray(new Character[0]);
	     //  System.out.println("Ключи строки для анализа:  " + keysMapForcomparison2);
	       for(int i=0;i<keysMapForcomparison2.length;i++) {
	    	//   System.out.println(i+"  " +keysMapForcomparison1[i]+"  " + keysMapForcomparison2[i]);
	    	   mapComparison.put(keysMapForcomparison1[i],keysMapForcomparison2[i]);	    	   
	       }
	       System.out.println("Сопоставленная карта: " + mapComparison);
	       
		 return mapComparison;	}
	//декодирование строки с помощью стат анализа
	public static String decodeStatMethod(HashMap<Character, Character> mapForcomparison, String strFor) {String rez="";
	char [] strForInChar=strFor.toCharArray();
	for(int i=0;i<strForInChar.length;i++) {
		if(mapForcomparison.containsKey(strForInChar[i])) {
		rez=rez+mapForcomparison.get(strForInChar[i]);}
		else rez=rez+strForInChar[i];
	}
		return rez;
	}
	//основной метод 
	public static String sifrovka(int zas)throws Exception { String rezult=""; 
	if(zas==0) {System.out.println("Вы выбрали не корректное действие! ");}
	else {
	Scanner sc = new Scanner(System.in);
	System.out.println("Введите путь к файлу, содержащему текст: ");
	String wayForFile = sc.nextLine();
	
	System.out.println("Введите путь к файлу для сохранения: ");
	 wayForRezult = sc.nextLine();
	 if(!wayForRezult.endsWith(".txt")){  wayForRezult=wayForRezult + ".txt";}
	 FileReader fr= new FileReader(wayForFile);
	 try {
	     Scanner scan = new Scanner(fr);
         
     int i = 1;
     String str="";String pr="";if(zas==1) {pr=" ";}
     while (scan.hasNextLine()) {
    	 str=str+scan.nextLine()+pr;
         i++;
     }

     fr.close();
	String str_for_work = str.toLowerCase();
	if(zas==1) {str_for_work =str_for_work.trim();}
	//System.out.println("Текст из файла:"+str_for_work);
	
		switch(zas) {
		case 1:
			System.out.println("Введите ключ");
			String kluch = sc.nextLine();
			int kay=getKey(kluch);//System.out.println(kay);
			rezult=kodDekod(str_for_work,kay);
		   break;
		case 2://System.out.println(STABC);
		    System.out.println("Введите ключ");
		    kluch = sc.nextLine();
		    kay=getKey('-'+kluch);//System.out.println(kay);
		    rezult=kodDekod(str_for_work,kay);
	       break;
		case 3: 
			int nidKluch=0; String varDekod; boolean pRcheckWhiteSpaces=false; boolean pRpunctuationMarks=false; boolean pRcountLit=false;
			for(i=0;i<STABC.length()-1;i++) {
				varDekod= kodDekod(str_for_work,i); pRcheckWhiteSpaces=checkWhiteSpaces(varDekod);pRpunctuationMarks=punctuationMarks(varDekod);pRcountLit=countLit(varDekod);
				if(pRcheckWhiteSpaces&&pRpunctuationMarks&&pRcountLit) {
					nidKluch=STABC.length()-i;rezult=kodDekod(str_for_work,i);
			 System.out.println("Файл успешно раскодирован! Подобранный ключ: "+nidKluch);
			 			break;
			}}
				if(!pRcheckWhiteSpaces||!pRpunctuationMarks||!pRcountLit) {System.out.println(" Не удалось подобрать ключ: ");}
				
	    break;
		case 4:
			System.out.println("Введите путь к файлу для анализа");
			 String wayForAnaliz= sc.nextLine();
			 FileReader fri= new FileReader(wayForAnaliz);
		        Scanner scans = new Scanner(fri);
		            
		        i = 1;
		        String strF="";
		        while (scans.hasNextLine()) {strF=strF+" "+scans.nextLine(); 
		            i++;
		        }
		        fri.close();
		       String strForAnaliz=  strF.toLowerCase().trim();
		     //  System.out.println("Анализa строкa "+strForAnaliz);
			 HashMap<Character, Double> mapStrShifr=ststAnaliz(str_for_work);
			// System.out.println("Анализ закодированной строки "+mapStrShifr);
			 HashMap<Character, Double> mapStr=ststAnaliz(strForAnaliz);
			// System.out.println("Анализ строки для анализа "+mapStr);
			 HashMap<Character, Character> deShyfr=comparisonMap(mapStrShifr,mapStr);
			 System.out.println("Сопоставления "+deShyfr);			 
			rezult=decodeStatMethod(deShyfr,str_for_work);break;
		}
		}
	catch (FileNotFoundException e){rezult="Вы указали неверное имя файла";}}
				return rezult;	
	}
	public static void main(String[] args)throws Exception { int zasi=0; boolean flag=true;  
	try
    {	
		Scanner sc = new Scanner(System.in);
		while(flag) {
			
		System.out.println("Выберите действие над текстом : 1 - Шифрование/Дешифрование с помощью ключа; 2 - Дешифратор без ключа");
		int rezhim = sc.nextInt();
		if(rezhim==1) {
			System.out.println("Выберите необходимое действие: 1 - Шифрование 2 - Дешифрование");
			int rezhim_shifr = sc.nextInt();
			if(rezhim_shifr==1) {
				zasi=1;flag=false;break;
			}
			else {if(rezhim_shifr==2) {zasi=2; flag=false;break;}
			else zasi=0;System.out.println("Вы выбрали некорректное действие!");}
			}
		
		else if(rezhim==2) {
			System.out.println("Выберите метод декодировки: 1 - brute force 2 - Синтаксический анализ");
			int rezhim_kriptoanaliza = sc.nextInt();
			if(rezhim_kriptoanaliza==1) {
				zasi=3;flag=false;break;
			}
			else {if(rezhim_kriptoanaliza==2) {zasi=4;flag=false;break;
			}
			else zasi=0;System.out.println("Вы выбрали не верный метод!");}
			
		
		} else {zasi=0;System.out.println("Что-то пошло не так(");}
		}
		String finalResult=sifrovka(zasi);
		//System.out.println("Text:"+finalResult);
		
		FileWriter nFile = new FileWriter(wayForRezult);   
         nFile.write(finalResult);
nFile.close();
		System.out.println("Результат записан в файл: "+wayForRezult);}
		 catch (InputMismatchException h)
		    {
			 System.out.println("Что-то пошло не так( Начните все с начала.");
		    }
	 catch (FileNotFoundException u )
    {
	 System.out.println("Вы ввели некорректное название файла, содержащего текст ( Начните все с начала.");
    }
	}

}
