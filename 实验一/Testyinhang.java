
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.StringTokenizer;

   
   abstract class Account1{
       //�����࣬�ֽ����û������ÿ��û����ࡣ
      String aid,name;
      double balance;//���
     public void deposit(){}//���
     public void withdraw(){}//ȡ��4   
     public void getBalance(){}//��ѯ���
     public void getMssage(){}//��ѯ��Ϣ
  }
   class CashAccount extends Account1
  {
      String cardtype;//��������
      CashAccount(){
          this.aid="";
          this.name="";
          this.balance=0;
      }
      CashAccount(String a,String b,double c,String d){
          this.aid=a;
          this.name=b;
          this.balance=c;
          this.cardtype=d;
      }
      /*
       *������д����
       */
      public void deposit(PrintWriter in,int i){
          Scanner s1=new Scanner(System.in);
          System.out.println("������Ҫ���������");
          double s=s1.nextDouble();
          balance=balance+s;
          System.out.println("���ɹ����������Ϊ"+balance);
          this.Writerecord(in, "���", s,i);//���ú���
      }
      /*
       * ȡ���,д����
       */
      public void withdraw(PrintWriter in,int i){
          System.out.println("������Ҫȡ������");
          Scanner S=new Scanner(System.in);
          double s=S.nextDouble();
          try{
          if(balance-s>0){
              balance=balance-s;
              System.out.println("ȡ��ɹ����������Ϊ"+balance);
              this.Writerecord(in, "ȡ��", -s,i);
          }
          else
              throw new WithdrawException("���㣡�������룡");
          }catch(WithdrawException e){System.out.println(e);
          this.getMssage(s);
          withdraw(in,i);}    
      }
      /*
       ��ʾ����,д�����
       */
      public void getBalance(PrintWriter in,int i){
          System.out.println("�������Ϊ"+balance);
          this.Writerecord(in, "��ѯ���", 0,i);
      }
      /*
       �ı俨���ͷ���
       */
      public void Changetype(PrintWriter in,int i){
          System.out.println("����ǰ�Ŀ�����Ϊ"+cardtype);
          System.out.println("ѡ������Ҫ�Ŀ�����\n1.���ʿ�\n2.��ǿ�\n3.��ƿ�");
          Scanner S=new Scanner(System.in);
          int s=S.nextInt();
          switch(s){
          case 1:cardtype="���ʿ�";break;
          case 2:cardtype="��ǿ�";break;
          case 3:cardtype="��ƿ�";break;
          }
          this.Writerecord(in, "�����������", 0,i);
      }
      /*
       * ��ʾ��Ϣ����*/
     public void getMssage(double a){
          System.out.println("ȡ����"+name+"\n"+"�˻����"+balance+"\n"+"ȡ���"+a+"\n"+"ԭ��͸֧");
      }
      /*
     * д�ļ��ķ���
       * @���� out��print writer*/
       public void writeData(PrintWriter out) throws IOException
         {
            out.println(aid + "|"
             + name + "|"
               + cardtype + "|"
               + Double.toString(balance));
        }
       /*
        * ���ļ��ķ���
        * @ in�Ĳ�����buffer
       */
      public void readData(BufferedReader in) throws IOException
        {
           String s = in.readLine();
           StringTokenizer t = new StringTokenizer(s, "|");
           aid=t.nextToken();
           name = t.nextToken();
           cardtype=t.nextToken();
           balance=t.countTokens();
        }
      /*
       * д������¼�ķ���       * ��������������ļ�·����print writer
       * */
      public  void Writerecord(PrintWriter in,String op,double a,int i) {
          String months[] = { 
                  "Jan", "Feb", "Mar", "Apr", 
                  "May", "Jun", "Jul", "Aug",
                  "Sep", "Oct", "Nov", "Dec"};
                  //��ʼ����ǰʱ�䲢�ں���д���ļ�
                  Calendar calendar = Calendar.getInstance();                
          in.println(String.valueOf(i)+"|"+aid + "|"
              + name + "|"+String.valueOf(calendar.get(Calendar.YEAR))+
                   "|"+String.valueOf(months[calendar.get(Calendar.MONTH)])+"|"+
             String.valueOf(calendar.get(Calendar.DATE))
              + "|"+String.valueOf(calendar.get(Calendar.HOUR))+"|"+
              String.valueOf(calendar.get(Calendar.MINUTE))
              +"|"+op+"|"
              + Double.toString(a));
         }
 }
 class WithdrawException extends Exception
 {
     WithdrawException(String s)
     {
    	 super(s);
     }
 }
  class CreditAccount extends Account1 {
     double lineOfCredit;//���õȼ���Ӧ�Ŀ�͸֧��
     String creditrate;//���õȼ�
     CreditAccount(){
         this.aid="";
         this.name="";
         this.balance=0;
         this.lineOfCredit=0;
         this.creditrate="A";
     }
     CreditAccount(String a,String b,double c,String d){
         this.aid=a;
         this.name=b;
         this.balance=c;
         this.creditrate=d;
         switch(d){
         case "A":lineOfCredit=10000;break;
         case "B":lineOfCredit=5000;break;
         case "C":lineOfCredit=2000;break;
         case "D":lineOfCredit=1000;break;
         }
     }
     /*
      * ����
      * */
     public void deposit(PrintWriter in,int i){
         System.out.println("������Ҫ���������");
         Scanner S=new Scanner(System.in);
         double s=S.nextDouble();
         balance=balance+s;
         System.out.println("���ɹ����������Ϊ"+balance);
         this.Writerecord(in, "���", s,i);
     }
     /*
      * ȡ���*/
     public void withdraw(PrintWriter in,int i){
         System.out.println("������Ҫȡ������");
         Scanner S=new Scanner(System.in);
         double s=S.nextDouble();
         try{
         if(balance-s>0){
             balance=balance-s;
             System.out.println("ȡ��ɹ����������Ϊ"+balance);
             this.Writerecord(in, "ȡ��", -s,i);
         }
         else
             if(lineOfCredit+balance-s>0){
                 balance=balance-s;
                 lineOfCredit=lineOfCredit+balance;
                 System.out.println("�������Ϊ"+balance+"���Ŀ�͸֧���Ϊ"+lineOfCredit);
             }
             else
             throw new WithdrawException("���㣡�������룡");
         }catch(WithdrawException e){System.out.println(e);
         this.getMssage(s);
         withdraw(in,i);}    
     }
     /*��ʾ����
      * */
     public void getBalance(PrintWriter in,int i){
         System.out.println("�������Ϊ"+balance);
         this.Writerecord(in, "��ѯ���", 0,i);
     }
     /*��ʾ��͸֧����
      * */
     public void findOverdraw(PrintWriter in,int i){
         System.out.println("���Ŀ�͸֧���Ϊ"+lineOfCredit);
         this.Writerecord(in, "��ѯ��͸֧���", 0,i);
     }
     /*��ʾ��Ϣ����
      * */
     public void getMssage(double a){
         System.out.println("ȡ����"+name+"\n"+"�˻����"+balance+"\n"+"ȡ���"+a+"\n"+"ԭ��͸֧");
     }
     /*
      * д�ļ��ķ���
      * @���� out��print writer*/
      public void writeData(PrintWriter out) throws IOException
        {
           out.println(aid + "|"
              + name + "|"+ creditrate + "|"
              + Double.toString(lineOfCredit) + "|"
              + Double.toString(balance));
        }
      /*
       * ���ļ��ķ���
       * @ in�Ĳ�����buffer
       */
      public void readData(BufferedReader in) throws IOException
        {
           String s = in.readLine();
           StringTokenizer t = new StringTokenizer(s, "|");
           aid=t.nextToken();
           name = t.nextToken();
           creditrate=t.nextToken();
           lineOfCredit=Double.parseDouble(t.nextToken());
           balance=Double.parseDouble(t.nextToken());
        }
      /*
       * д������¼�ķ���
       * ��������������ļ�·����print writer
       * */
      public  void Writerecord(PrintWriter in,String op,double a,int i) {
          String months[] = { 
                  "Jan", "Feb", "Mar", "Apr", 
                  "May", "Jun", "Jul", "Aug",
                  "Sep", "Oct", "Nov", "Dec"};
                  //��ʼ����ǰʱ�䲢�ں���д���ļ�
                  Calendar calendar = Calendar.getInstance();                
          in.println(String.valueOf(i)+"|"+aid + "|"
              + name + "|"+String.valueOf(calendar.get(Calendar.YEAR))+
                   "|"+months[calendar.get(Calendar.MONTH)]+"|"+
              String.valueOf(calendar.get(Calendar.DATE))
              + "|"+String.valueOf(calendar.get(Calendar.HOUR))+"|"+
              String.valueOf(calendar.get(Calendar.MINUTE))+"|"+
              op+"|"+Double.toString(a));
         }
 }

  class Record 
 {
 String aid,name,operation;
 int number,year,month,data,hour,minute;
 double money;
 /*
  * ��ȡ������¼�ķ���*/
 public void readData(BufferedReader in)throws IOException{
     int i=0;
     String s = in.readLine();
     StringTokenizer t = new StringTokenizer(s, "|");
     number=Integer.parseInt(t.nextToken());
     aid=t.nextToken();
     name = t.nextToken();
     year=Integer.parseInt(t.nextToken());
     switch(t.nextToken()){
     case "Jan":i=1;break;
     case "Feb":i=2;break;
     case "Mar":i=3;break;
     case "Apr":i=4;break;
     case "May":i=5;break;
     case "Jun":i=6;break;
     case "Jul":i=7;break;case "Aug":i=9;break;
     case "Sep":i=9;break;case "Oct":i=10;break; case"Nov":i=11;break;case "Dec":i=12;break;
     }
     month=i;
     data=Integer.parseInt(t.nextToken());
     hour=Integer.parseInt(t.nextToken());
     minute=Integer.parseInt(t.nextToken());
     operation=t.nextToken();
     money=Double.parseDouble(t.nextToken());
 }
 /*
  * ͳ�Ʋ�����¼�ķ���
  * �����ֱ�Ϊ 
  * ��¼���� 
  * 1����aid��ѯ 2�������ڲ�ѯ
  * s��ʾaid*/
 public void Sort(Record[] a,int i,String s){
     if(i==1)//����aid��ѯ���м�¼
     {
         System.out.println("���Ĳ�����¼Ϊ");
         for (Record e : a)
         if(e.aid.equals(s))
             System.out.println(e);
     }
     else if(i==2)//����aid�����ڲ��Ҽ�¼
     {
         System.out.println("������������ҵ�ʱ������");
         Scanner q=new Scanner(System.in);
         System.out.println("������������ҵ���ʼ��,��ʽΪ2016");
         int tyear=q.nextInt();
         System.out.println("������������ҵ���ʼ��,��ʽΪ3");
         int tmonth=q.nextInt();
         System.out.println("������������ҵ���ʼ����,��ʽΪ1");
         int tdata=q.nextInt();
         System.out.println("������������ҵ���ֹ��,��ʽΪ2016");
         int tyeare=q.nextInt();
         System.out.println("������������ҵ���ֹ��,��ʽΪ3");
         int tmonthe=q.nextInt();
         System.out.println("������������ҵ���ֹ����,��ʽΪ1");
         int tdatae=q.nextInt();
         for (Record e : a)
             if(e.aid.equals(s)&&e.year>=tyear&&e.year<=tyeare&&e.month>=tmonth&&e.month<=tmonthe
             &&e.data>=tdata&&e.data<=tdatae)
                 System.out.println(e);
         q.close();
     }
 }
 //������ת��Ϊ�ַ���
 public String toString()
 {
    return getClass().getName()
       + "[number="+String.valueOf(number)+" aid=" + aid
       + ",name=" + name+" "+String.valueOf(year)+"/"+String.valueOf(month)+"/"+
       String.valueOf(data)+"/"+String.valueOf(hour)+"/"+String.valueOf(minute)+"/"
       + "����:" + operation+" ���: "+String.valueOf(money)
       + "]";
 }
 }

 public class Testyinhang {
     public static void main(String[] args){
         int number=1;
         CashAccount[] a=new CashAccount[3];
         CreditAccount[] b=new CreditAccount[3];
         a[0]=new CashAccount("20163584","xu",100,"��ǿ�");
         a[1]=new CashAccount("21603584","xujinyan",500,"��ǿ�");
         a[2]=new CashAccount("21063584","yan",100,"��ǿ�");
         b[0]=new CreditAccount("04","d",100,"A");
         b[1]=new CreditAccount("05","e",100,"A");
         b[2]=new CreditAccount("06","f",100,"A");
          try
           {
              // д���ļ�����ĩβд��  �ֱ�д�ֽ���������ÿ�����
              //���ۼ�¼���ݻ��Ǽ�¼�����������ֽ�����ÿ��ֱ����ļ� 1��Ӧ���ÿ�
              PrintWriter out = new PrintWriter(new FileWriter("D://ClientInfoTable.txt"),true);
              PrintWriter out1 = new PrintWriter(new FileWriter("D://ClientInfoTable1.txt"),true);
              PrintWriter in = new PrintWriter(new FileWriter("D://BankInfoTable.txt",true));
              PrintWriter in1 = new PrintWriter(new FileWriter("D://BankInfoTable1.txt",true));
              in.println("���"+"|"+"����"+"|"+"����"+"|"+"��"+"|"+"��"+"|"+"��"+"|"+"ʱ"+"|"+"��"+"|"+"��������"+"|"+"���");
              in1.println("���"+"|"+"����"+"|"+"����"+"|"+"��"+"|"+"��"+"|"+"��"+"|"+"ʱ"+"|"+"��"+"|"+"��������"+"|"+"���");
              //out.println("����"+"|"+"����"+"|"+"������"+"|"+"���");
              out1.println("����"+"|"+"����"+"|"+"���õȼ�"+"|"+"��͸֧��"+"|"+"���"); 
              writeDatacash(a, out);
              writeDatacredit(b, out1);
              do{
                  try{
                      System.out.print("��ѡ���û�����\t1.�ֽ�\t2.���ÿ�");
                      Scanner s=new Scanner(System.in);
                      int i=s.nextInt();
                      if(i==1)
                          {showface(a[i],in,number);
                          in.flush();}
                      else if(i==2)
                          {showface(a[i],in1,number);
                          in.flush();}
                      else
                          throw new In("���󣡴�1��2��ѡ������");
                  }catch(In e){System.out.print(e);
                  System.exit(0);}
                  number++;
              }while(number<=6);   
              System.out.print("��ͳ��һ��");
              Statistics();
              out.checkError();
              out1.close();
              in.close();
              in1.close();
           }
           catch(IOException exception)
           {
              exception.printStackTrace();
           }
        }
     //�ֽ��û�����
     static void showface(CashAccount a,PrintWriter in,int number){
         String s="*****************************************\n";
         s=s+"          ģ������ATMϵͳ    \n";
         s=s+"*****************************************\n";
         s=s+"    1�����\n";
         s=s+"    2��ȡ��\n";
         s=s+"    3����ѯ���\n";
         s=s+"    4���ı俨������\n";
         s=s+"    5������5����\n";
         do{
             System.out.println(s);
             Scanner S1=new Scanner(System.in);
             int i=S1.nextInt();
             if(i==1)
                 a.deposit(in,number);        
             else if(i==2)
                 a.withdraw(in,number);
             else if(i==3)
                 a.getBalance(in,number);
             else if(i==4)
                 a.Changetype(in,number);
             else
                 break;
             }while(true);
     }
     //���ÿ��û�����
     static void showface(CreditAccount a,PrintWriter in,int number){
         String s1="*****************************************\n";
         s1=s1+"          ģ������ATMϵͳ    \n";
         s1=s1+"*****************************************\n";
         s1=s1+"    1�����\n";
         s1=s1+"    2��ȡ��\n";
         s1=s1+"    3����ѯ���\n";
         s1=s1+"    4����ѯ��͸֧���\n";
         s1=s1+"    5������5����\n";
         do{
             System.out.println(s1);
             Scanner S1=new Scanner(System.in);
             int i=S1.nextInt();
             if(i==1)
                 a.deposit(in,number);        
             else if(i==2)
                 a.withdraw(in,number);
             else if(i==3)
                 a.getBalance(in,number);
             else if(i==4)
                 a.findOverdraw(in,number);
             else
                 break;
             }while(true);
     }
      /**
     ����Ϣд���ļ����ֽ��û�
     @param out a print writer
  */
  static void writeDatacash(CashAccount[] employees, PrintWriter out)
     throws IOException
  {
     // write number of employees
     out.println(employees.length);

     for (CashAccount e : employees)
        e.writeData(out);
  }
  /**
  ����Ϣд���ļ�,���ÿ��û�
  @param out a print writer
 */
 static void writeDatacredit(CreditAccount[] account, PrintWriter out)
  throws IOException
 {
  // write number of employees
  out.println(account.length);

  for (CreditAccount e : account)
     e.writeData(out);
 }
  /**
     ����¼��Ϣ����Record������
  */
  static Record[] readDatarecord(BufferedReader in)
     throws IOException
  {
     Record[] a = new Record[6];
     for (int i = 0; i < 6; i++)
     {
        a[i] = new Record();
        a[i].readData(in);
     }
     return a;
  }
  /**
  ���ֽ��û�
  @param in the buffered reader
  @return the array of employees
 */
 static CashAccount[] readData(BufferedReader in)
  throws IOException
 {
  // retrieve the array size
  int n = Integer.parseInt(in.readLine());

  CashAccount[] employees = new CashAccount[n];
  for (int i = 0; i < n; i++)
  {
     employees[i] = new CashAccount();
     employees[i].readData(in);
  }
  return employees;
 }
  /**
  Reads an array of ���ÿ��û� from a buffered reader���ļ���Ϣ��Ϊ����
  @param in the buffered reader
  @return the array of employees
 */
 static CreditAccount[] readDatacredit(BufferedReader in)
  throws IOException
 {
  CreditAccount[] a = new CreditAccount[6];
  for (int i = 0; i < 6; i++)
  {
     a[i] = new CreditAccount();
     a[i].readData(in);;
  }
  return a;
 }
 /*
  * ʵ��ͳ�ƹ���
  * */
 static void Statistics()throws IOException{
      // ���ļ��е����ݶ���һ��������
     BufferedReader in = new BufferedReader(new FileReader("C:\\BankInfoTable.txt"));
     String t=in.readLine();
     BufferedReader in1 = new BufferedReader(new FileReader("C:\\BankInfoTable1.txt"));
     String t1=in1.readLine();
     do{
         try{
             System.out.print("��ѡ���û�����\n1.�ֽ�\n2.���ÿ�\n3.�˳�");
              Scanner s=new Scanner(System.in);
              int i=s.nextInt();
              Record a=new Record();
              if(i==1)
              {Record[] newcashaccount=readDatarecord(in);
              System.out.println("ѡ���ѯ��ʽ��\n1.��ѯ����������ʷ������¼\n2.�����ڲ�ѯ");
              int j=s.nextInt();
              if(j==1)
                  a.Sort(newcashaccount, 1, "02");
              else
                  a.Sort(newcashaccount, 2, "02");
              }
              else if(i==2)
              {Record[] newcashaccount1=readDatarecord(in1);
              System.out.println("ѡ���ѯ��ʽ��\n1.��ѯ����������ʷ������¼\n2.�����ڲ�ѯ");
              int j=s.nextInt();
              if(j==1)
                  a.Sort(newcashaccount1, 1, "02");
              else
                  a.Sort(newcashaccount1, 2, "02");} 
              else if(i==3)
                  break;
              else
                  throw new In("���󣡴�1��2��ѡ������");
         }catch(In e){System.out.print(e);
         System.exit(0);}
         
     }while(true);     
     in.close();
     in1.close();
 }
 }
 class In extends Exception{
     In(String s){super(s);}
 }
