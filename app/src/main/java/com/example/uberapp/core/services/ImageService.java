package com.example.uberapp.core.services;

import com.example.uberapp.core.dto.PictureResponseDTO;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ImageService {
    @GET("/api/image/{imgName}")
    Observable<ResponseBody> getImage(@Path("imgName") String imgName);
    @GET("/api/image/profilePicture/{pictureName}")
    Call<ResponseBody> getProfilePicture(@Path("pictureName") String pictureName);
    @Multipart
    @POST("/api/image/{userId}/profilePicture")
    Call<PictureResponseDTO> uploadProfilePicture(@Path("userId") Integer userId, @Part MultipartBody.Part filePart);
    @GET("/api/image/document/{documentName}")
    Call<ResponseBody> getDocumentPicture(@Path("documentName") String documentName);
}
