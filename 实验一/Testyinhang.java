
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.StringTokenizer;

   
   abstract class Account1{
       //抽象类，现金类用户和信用卡用户父类。
      String aid,name;
      double balance;//余额
     public void deposit(){}//存款
     public void withdraw(){}//取款4   
     public void getBalance(){}//查询余额
     public void getMssage(){}//查询信息
  }
   class CashAccount extends Account1
  {
      String cardtype;//卡的类型
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
       *存款方法，写操作
       */
      public void deposit(PrintWriter in,int i){
          Scanner s1=new Scanner(System.in);
          System.out.println("请输入要存入的数额");
          double s=s1.nextDouble();
          balance=balance+s;
          System.out.println("存款成功！您的余额为"+balance);
          this.Writerecord(in, "存款", s,i);//调用函数
      }
      /*
       * 取款方法,写操作
       */
      public void withdraw(PrintWriter in,int i){
          System.out.println("请输入要取的数额");
          Scanner S=new Scanner(System.in);
          double s=S.nextDouble();
          try{
          if(balance-s>0){
              balance=balance-s;
              System.out.println("取款成功！您的余额为"+balance);
              this.Writerecord(in, "取款", -s,i);
          }
          else
              throw new WithdrawException("余额不足！重新输入！");
          }catch(WithdrawException e){System.out.println(e);
          this.getMssage(s);
          withdraw(in,i);}    
      }
      /*
       显示余额方法,写入操作
       */
      public void getBalance(PrintWriter in,int i){
          System.out.println("您的余额为"+balance);
          this.Writerecord(in, "查询余额", 0,i);
      }
      /*
       改变卡类型方法
       */
      public void Changetype(PrintWriter in,int i){
          System.out.println("您当前的卡类型为"+cardtype);
          System.out.println("选择您需要的卡类型\n1.工资卡\n2.借记卡\n3.理财卡");
          Scanner S=new Scanner(System.in);
          int s=S.nextInt();
          switch(s){
          case 1:cardtype="工资卡";break;
          case 2:cardtype="借记卡";break;
          case 3:cardtype="理财卡";break;
          }
          this.Writerecord(in, "变更卡的类型", 0,i);
      }
      /*
       * 显示信息方法*/
     public void getMssage(double a){
          System.out.println("取款人"+name+"\n"+"账户余额"+balance+"\n"+"取款额"+a+"\n"+"原因：透支");
      }
      /*
     * 写文件的方法
       * @参数 out在print writer*/
       public void writeData(PrintWriter out) throws IOException
         {
            out.println(aid + "|"
             + name + "|"
               + cardtype + "|"
               + Double.toString(balance));
        }
       /*
        * 独文件的方法
        * @ in的参数在buffer
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
       * 写操作记录的方法       * 参数来自主类的文件路径和print writer
       * */
      public  void Writerecord(PrintWriter in,String op,double a,int i) {
          String months[] = { 
                  "Jan", "Feb", "Mar", "Apr", 
                  "May", "Jun", "Jul", "Aug",
                  "Sep", "Oct", "Nov", "Dec"};
                  //初始化当前时间并在后续写入文件
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
     double lineOfCredit;//信用等级对应的可透支额
     String creditrate;//信用等级
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
      * 存款方法
      * */
     public void deposit(PrintWriter in,int i){
         System.out.println("请输入要存入的数额");
         Scanner S=new Scanner(System.in);
         double s=S.nextDouble();
         balance=balance+s;
         System.out.println("存款成功！您的余额为"+balance);
         this.Writerecord(in, "存款", s,i);
     }
     /*
      * 取款方法*/
     public void withdraw(PrintWriter in,int i){
         System.out.println("请输入要取的数额");
         Scanner S=new Scanner(System.in);
         double s=S.nextDouble();
         try{
         if(balance-s>0){
             balance=balance-s;
             System.out.println("取款成功！您的余额为"+balance);
             this.Writerecord(in, "取款", -s,i);
         }
         else
             if(lineOfCredit+balance-s>0){
                 balance=balance-s;
                 lineOfCredit=lineOfCredit+balance;
                 System.out.println("您的余额为"+balance+"您的可透支余额为"+lineOfCredit);
             }
             else
             throw new WithdrawException("余额不足！重新输入！");
         }catch(WithdrawException e){System.out.println(e);
         this.getMssage(s);
         withdraw(in,i);}    
     }
     /*显示余额方法
      * */
     public void getBalance(PrintWriter in,int i){
         System.out.println("您的余额为"+balance);
         this.Writerecord(in, "查询余额", 0,i);
     }
     /*显示可透支余额方法
      * */
     public void findOverdraw(PrintWriter in,int i){
         System.out.println("您的可透支余额为"+lineOfCredit);
         this.Writerecord(in, "查询可透支余额", 0,i);
     }
     /*显示信息方法
      * */
     public void getMssage(double a){
         System.out.println("取款人"+name+"\n"+"账户余额"+balance+"\n"+"取款额"+a+"\n"+"原因：透支");
     }
     /*
      * 写文件的方法
      * @参数 out在print writer*/
      public void writeData(PrintWriter out) throws IOException
        {
           out.println(aid + "|"
              + name + "|"+ creditrate + "|"
              + Double.toString(lineOfCredit) + "|"
              + Double.toString(balance));
        }
      /*
       * 独文件的方法
       * @ in的参数在buffer
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
       * 写操作记录的方法
       * 参数来自主类的文件路径和print writer
       * */
      public  void Writerecord(PrintWriter in,String op,double a,int i) {
          String months[] = { 
                  "Jan", "Feb", "Mar", "Apr", 
                  "May", "Jun", "Jul", "Aug",
                  "Sep", "Oct", "Nov", "Dec"};
                  //初始化当前时间并在后续写入文件
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
  * 读取操作记录的方法*/
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
  * 统计操作记录的方法
  * 参数分别为 
  * 记录数组 
  * 1代表按aid查询 2代表按日期查询
  * s表示aid*/
 public void Sort(Record[] a,int i,String s){
     if(i==1)//按照aid查询所有记录
     {
         System.out.println("您的操作记录为");
         for (Record e : a)
         if(e.aid.equals(s))
             System.out.println(e);
     }
     else if(i==2)//按照aid和日期查找记录
     {
         System.out.println("请输入您想查找的时间区间");
         Scanner q=new Scanner(System.in);
         System.out.println("请输入您想查找的起始年,格式为2016");
         int tyear=q.nextInt();
         System.out.println("请输入您想查找的起始月,格式为3");
         int tmonth=q.nextInt();
         System.out.println("请输入您想查找的起始日期,格式为1");
         int tdata=q.nextInt();
         System.out.println("请输入您想查找的终止年,格式为2016");
         int tyeare=q.nextInt();
         System.out.println("请输入您想查找的终止月,格式为3");
         int tmonthe=q.nextInt();
         System.out.println("请输入您想查找的终止日期,格式为1");
         int tdatae=q.nextInt();
         for (Record e : a)
             if(e.aid.equals(s)&&e.year>=tyear&&e.year<=tyeare&&e.month>=tmonth&&e.month<=tmonthe
             &&e.data>=tdata&&e.data<=tdatae)
                 System.out.println(e);
         q.close();
     }
 }
 //将对象转换为字符串
 public String toString()
 {
    return getClass().getName()
       + "[number="+String.valueOf(number)+" aid=" + aid
       + ",name=" + name+" "+String.valueOf(year)+"/"+String.valueOf(month)+"/"+
       String.valueOf(data)+"/"+String.valueOf(hour)+"/"+String.valueOf(minute)+"/"
       + "操作:" + operation+" 金额: "+String.valueOf(money)
       + "]";
 }
 }

 public class Testyinhang {
     public static void main(String[] args){
         int number=1;
         CashAccount[] a=new CashAccount[3];
         CreditAccount[] b=new CreditAccount[3];
         a[0]=new CashAccount("20163584","xu",100,"借记卡");
         a[1]=new CashAccount("21603584","xujinyan",500,"借记卡");
         a[2]=new CashAccount("21063584","yan",100,"借记卡");
         b[0]=new CreditAccount("04","d",100,"A");
         b[1]=new CreditAccount("05","e",100,"A");
         b[2]=new CreditAccount("06","f",100,"A");
          try
           {
              // 写入文件，从末尾写入  分别写现金操作和信用卡操作
              //不论记录数据还是记录操作，都按现金和信用卡分别建立文件 1对应信用卡
              PrintWriter out = new PrintWriter(new FileWriter("D://ClientInfoTable.txt"),true);
              PrintWriter out1 = new PrintWriter(new FileWriter("D://ClientInfoTable1.txt"),true);
              PrintWriter in = new PrintWriter(new FileWriter("D://BankInfoTable.txt",true));
              PrintWriter in1 = new PrintWriter(new FileWriter("D://BankInfoTable1.txt",true));
              in.println("序号"+"|"+"卡号"+"|"+"姓名"+"|"+"年"+"|"+"月"+"|"+"日"+"|"+"时"+"|"+"分"+"|"+"操作类型"+"|"+"金额");
              in1.println("序号"+"|"+"卡号"+"|"+"姓名"+"|"+"年"+"|"+"月"+"|"+"日"+"|"+"时"+"|"+"分"+"|"+"操作类型"+"|"+"金额");
              //out.println("卡号"+"|"+"姓名"+"|"+"卡类型"+"|"+"余额");
              out1.println("卡号"+"|"+"姓名"+"|"+"信用等级"+"|"+"可透支额"+"|"+"余额"); 
              writeDatacash(a, out);
              writeDatacredit(b, out1);
              do{
                  try{
                      System.out.print("请选择用户类型\t1.现金卡\t2.信用卡");
                      Scanner s=new Scanner(System.in);
                      int i=s.nextInt();
                      if(i==1)
                          {showface(a[i],in,number);
                          in.flush();}
                      else if(i==2)
                          {showface(a[i],in1,number);
                          in.flush();}
                      else
                          throw new In("错误！从1或2中选择输入");
                  }catch(In e){System.out.print(e);
                  System.exit(0);}
                  number++;
              }while(number<=6);   
              System.out.print("来统计一下");
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
     //现金用户界面
     static void showface(CashAccount a,PrintWriter in,int number){
         String s="*****************************************\n";
         s=s+"          模拟银行ATM系统    \n";
         s=s+"*****************************************\n";
         s=s+"    1、存款\n";
         s=s+"    2、取款\n";
         s=s+"    3、查询余额\n";
         s=s+"    4、改变卡的种类\n";
         s=s+"    5、输入5结束\n";
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
     //信用卡用户界面
     static void showface(CreditAccount a,PrintWriter in,int number){
         String s1="*****************************************\n";
         s1=s1+"          模拟银行ATM系统    \n";
         s1=s1+"*****************************************\n";
         s1=s1+"    1、存款\n";
         s1=s1+"    2、取款\n";
         s1=s1+"    3、查询余额\n";
         s1=s1+"    4、查询可透支余额\n";
         s1=s1+"    5、输入5结束\n";
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
     将信息写入文件，现金用户
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
  将信息写入文件,信用卡用户
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
     将记录信息读进Record类数组
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
  读现金用户
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
  Reads an array of 信用卡用户 from a buffered reader将文件信息读为数组
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
  * 实现统计功能
  * */
 static void Statistics()throws IOException{
      // 将文件中的内容读进一个新数组
     BufferedReader in = new BufferedReader(new FileReader("C:\\BankInfoTable.txt"));
     String t=in.readLine();
     BufferedReader in1 = new BufferedReader(new FileReader("C:\\BankInfoTable1.txt"));
     String t1=in1.readLine();
     do{
         try{
             System.out.print("请选择用户类型\n1.现金卡\n2.信用卡\n3.退出");
              Scanner s=new Scanner(System.in);
              int i=s.nextInt();
              Record a=new Record();
              if(i==1)
              {Record[] newcashaccount=readDatarecord(in);
              System.out.println("选择查询方式：\n1.查询您的所有历史操作记录\n2.按日期查询");
              int j=s.nextInt();
              if(j==1)
                  a.Sort(newcashaccount, 1, "02");
              else
                  a.Sort(newcashaccount, 2, "02");
              }
              else if(i==2)
              {Record[] newcashaccount1=readDatarecord(in1);
              System.out.println("选择查询方式：\n1.查询您的所有历史操作记录\n2.按日期查询");
              int j=s.nextInt();
              if(j==1)
                  a.Sort(newcashaccount1, 1, "02");
              else
                  a.Sort(newcashaccount1, 2, "02");} 
              else if(i==3)
                  break;
              else
                  throw new In("错误！从1或2中选择输入");
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
