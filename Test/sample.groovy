// メソッドの定義
def add(a, b) {
    a + b
}

def insertspan(sei,mei,seispan,smspan,meispan){
  for (int i = 1; i < sei.length(); i+=2)
  {
    sei.insert(i,seispan);
  }
  for (int i = 1; i < mei.length(); i+=2)
  {
    mei.insert(i,meispan);
  }
  sei + smspan + mei;
}

int spanheight(x,h){
  x + h;
}

// メソッドの呼び出し
def a=100;
def b=350;
def x=0;
def val = add(a, b)
println val

StringBuilder sei = new StringBuilder();
StringBuilder mei = new StringBuilder();
sei.append('田井中');
mei.append('一太郎');

def seispan = '_', smspan = '＿', meispan = '_';

def Shimei  = insertspan(sei,mei,seispan,smspan,meispan)

println Shimei

x=spanheight(x,b)

println "1回め"+x

x=spanheight(x,15)

println "2回め"+x
