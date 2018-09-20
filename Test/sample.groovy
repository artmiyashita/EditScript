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
def list0 = [1,2,3];
def list1 = [5,7,11];
def list2 = [13,17,23];
def list =[list0,list1]


def jidoriBuilder(sei,map,positionX){

  map['pSeiX'] = positionX;
  map['pMeiX'] = positionX + sei.length() + map['smspan'];
  return map;
}

def list3 =['name':'taro', 'age':15]

// メソッドの呼び出し

def positionX = 28;
def sei = "田";
def mei = "太郎";
def map = ['pSeiX':0,'pMeiX':0,'smspan':5];
//JidoriX[姓文字数、名文字数、姓名間全角スペース比、姓スペース(pt)、名スペース(pt)]
def jidori = [
  [1,1,2,0,0],
  [1,2,2,0,0],
  [1,3,2,7,7],
  [2,1,2,7,7],
  [2,2,1,7,7],
  [2,3,1,7,7],
  [3,1,2,7,7],
  [3,2,1,7,7],
  [3,3,1,3.5,3.5],
  [0,0,0.5,0,0]
  ];
jidoriBuilder(sei,map,positionX);

println map['pSeiX'];
