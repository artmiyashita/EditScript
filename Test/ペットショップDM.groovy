@import injectionValiable
env.injectionOne = {cassette, record, labelList, imageTable ->
 myInjectionOne(cassette, record, labelList, imageTable);
};
if(!env.multiLayout) {
 doProduce(2, env.imageTable); // ページ数 2
}

//独自の刺し込み処理
def myInjectionOne(cassette, record, labelList, imageTable) {

  def additionalLabelList = ['郵便バーコード','氏名'];

  postnum = record['郵便番号'].replace('-','');
  record['郵便バーコード'] = postnum;

  record['氏名'] = record['姓']　+ ' ' +　record['名'];

  //基本関数
  labelList.each {
    injectionOneParts(cassette, it , record, imageTable);
  }
  //追加ラベルへの差し込み
  additionalLabelList.each {
    injectionOneParts(cassette, it , record, imageTable);
  }

  //表面の判定
  def omote = getPartsByLabel('郵便番号', 1, cassette) ;
  //表面の処理ここから
  if(omote != null){

    for (int i=0; i<7; i++){
      getPartsByLabel('郵便番号',i+1,cassette).param.text = postnum.substring(i,i+1);
    }

  }else{

  }
}
