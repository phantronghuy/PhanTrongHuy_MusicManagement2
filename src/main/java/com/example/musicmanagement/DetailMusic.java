package com.example.musicmanagement;

public class DetailMusic extends Item{
    private String tacGia;
    private String namPhatHanh;
    DetailMusic(){

    }

    DetailMusic(String maSo, String tenBai, int thich , String tacGia, String namPhatHanh){
        super(maSo,tenBai,thich);
        this.tacGia=tacGia;
        this.namPhatHanh=namPhatHanh;
    }

    DetailMusic(Item parent,String tacGia,String namPhatHanh){
        new DetailMusic(parent.getMaso(),parent.getTieude(),parent.getThich(),tacGia,namPhatHanh);
    }
    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNamPhatHanh() {
        return namPhatHanh;
    }

    public void setNamPhatHanh(String namPhatHanh) {
        this.namPhatHanh = namPhatHanh;
    }

    @Override
    public String toString(){
        String rs=super.toString();
        rs=rs+" "+tacGia+" "+namPhatHanh;
        return rs;
    }
}

