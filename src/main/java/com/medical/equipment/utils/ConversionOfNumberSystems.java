package com.medical.equipment.utils;

public class ConversionOfNumberSystems {

    private static String hexStr =  "0123456789ABCDEF";
    private static String[] binaryArray =
            {"0000","0001","0010","0011",
                    "0100","0101","0110","0111",
                    "1000","1001","1010","1011",
                    "1100","1101","1110","1111"};

    /**
     *
     * @param
     * @return 二进制数组转换为二进制字符串   2-2
     */
    public static String bytes2BinStr(byte[] bArray){

        String outStr = "";
        int pos = 0;
        for(byte b:bArray){
            //高四位
            pos = (b&0xF0)>>4;
            outStr+=binaryArray[pos];
            //低四位
            pos=b&0x0F;
            outStr+=binaryArray[pos];
        }
        return outStr;
    }

    /**
     *
     * @param bytes
     * @return 将二进制数组转换为十六进制字符串  2-16
     */
    public static String bin2HexStr(byte[] bytes){

        String result = "";
        String hex = "";
        for(int i=0;i<bytes.length;i++){
            //字节高4位
            hex = String.valueOf(hexStr.charAt((bytes[i]&0xF0)>>4));
            //字节低4位
            hex += String.valueOf(hexStr.charAt(bytes[i]&0x0F));
            result +=hex;  //+" "
        }
        return result;
    }

    /**
     *
     * @param hexString
     * @return 将十六进制转换为二进制字节数组   16-2
     */
    public static byte[] hexStr2BinArr(String hexString){
        //hexString的长度对2取整，作为bytes的长度
        int len = hexString.length()/2;
        byte[] bytes = new byte[len];
        byte high = 0;//字节高四位
        byte low = 0;//字节低四位
        for(int i=0;i<len;i++){
            //右移四位得到高位
            high = (byte)((hexStr.indexOf(hexString.charAt(2*i)))<<4);
            low = (byte)hexStr.indexOf(hexString.charAt(2*i+1));
            bytes[i] = (byte) (high|low);//高地位做或运算
        }
        return bytes;
    }

    /**
     *
     * @param hexString
     * @return 将十六进制转换为二进制字符串   16-2
     */
    public static String hexStr2BinStr(String hexString){
        return bytes2BinStr(hexStr2BinArr(hexString));
    }




    /**
     * 十进制数转为2进制数
     * @param num 十进制数
     * @param size 返回的位数 （可以根据自己需求设置）
     * @return
     */
    public static String decimalToBinary(int num, int size) {
        if (size <(Integer.SIZE - Integer.numberOfLeadingZeros(num))) {
            throw  new RuntimeException("传入size小于num二进制位数");
        }
        StringBuilder binStr = new StringBuilder();
        for(int i = size-1;i >= 0; i--){
            binStr.append(num >>> i & 1);
        }
        return binStr.toString();
    }



}
