@import injectionValiable
env.injectionOne = {cassette, record, labelList, imageTable ->
 myInjectionOne(cassette, record, labelList, imageTable);
};
if(!env.multiLayout) {
 doProduce(2, env.imageTable); // ページ数 1
}

//独自の刺し込み処理
def myInjectionOne(cassette, record, labelList, imageTable) {
  //ラベルの追加
  def additionalLabelList = ["結合住所"];

  //表面住所データ結合
  def postnum = record['郵便番号'];
  def adr1 = record['住所1'];
  def adr2 = record['住所2'];
  def address = postnum + adr1 + adr2;

  record['結合住所'] = address;

  //基本関数
 labelList.each {
   injectionOneParts(cassette, it , record, imageTable);
 }

 //追加ラベルへの差し込み
 additionalLabelList.each{
   injectionOneParts(cassette, it , record, imageTable);
 }

}
