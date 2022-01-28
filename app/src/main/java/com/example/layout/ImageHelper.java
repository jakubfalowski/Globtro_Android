//package com.example.layout;
//
//import android.net.Uri;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class ImageHelper {
//
//    public byte[] uriToBytes(Uri uri){
//
//        InputStream iStream =   getContentResolver().openInputStream(uri);
//        byte[] inputData = getBytes(iStream);
//
//    }
//
//    public byte[] getBytes(InputStream inputStream) throws IOException {
//        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
//        int bufferSize = 1024;
//        byte[] buffer = new byte[bufferSize];
//
//        int len = 0;
//        while ((len = inputStream.read(buffer)) != -1) {
//            byteBuffer.write(buffer, 0, len);
//        }
//        return byteBuffer.toByteArray();
//    }
//
//
//}
