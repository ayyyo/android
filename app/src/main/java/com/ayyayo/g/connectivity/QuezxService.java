package com.ayyayo.g.connectivity;

import com.ayyayo.g.model.AccessToken;
import com.ayyayo.g.model.ApplicantModel;
import com.ayyayo.g.model.ChangeStateModel;
import com.ayyayo.g.model.CommentModel;
import com.ayyayo.g.model.JobFollowerModel;
import com.ayyayo.g.model.JobModel;
import com.ayyayo.g.model.JobStateChangeModel;
import com.ayyayo.g.model.ReasonModel;
import com.ayyayo.g.model.RequestBodyModel;
import com.ayyayo.g.model.StateModel;
import com.ayyayo.g.model.StatusModel;
import com.ayyayo.g.model.UserModel;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Streaming;

public interface QuezxService {

  @FormUrlEncoded
  @POST("/oauth/token")
  Call<AccessToken> loginUser(@Field("username") String first);

  @FormUrlEncoded
  @POST("/oauth/token")
  Call<AccessToken> refreshToken(@Field("grant_type") String grant_type,
                                 @Field("refresh_token") String refresh_token);

  @GET("/api/users/me")
  Call<UserModel> getUserInfo();

  @POST("/api/logout")
  Call<ResponseBody> logout(@Body AccessToken accessToken);

  @GET("/api/users/states")
  Call<List<StateModel>> getStates();

  @GET("/api/reasons")
  Call<List<ReasonModel>> getReasons();

  @GET("/api/jobs/allocationStatusNew")
  Call<ResponseBody> getJobs(@Query("limit") int limit, @Query("offset") int offset,
                             @Query("status") String status, @Query("q") String searchKey);

  @GET("/api/jobs/{job_id}")
  Call<JobModel> getJobDetail(@Path("job_id") int job_id);

  @GET("/api/jobs/{job_id}/followers")
  Call<List<JobFollowerModel>> getJobFollowers(@Path("job_id") int jobId);

  @POST("/api/jobs/{job_id}/consultantResponse")
  Call<ResponseBody> updateJobStatus(@Path("job_id") int jobId, @Body JobStateChangeModel body);

  @GET("/api/jobs/{job_id}/applicants")
  Call<List<StatusModel>> getJobApplicants(@Path("job_id") int jobId, @Query("status") String state,
                                           @Query("offset") int start, @Query("limit") int rows);

  @POST("/api/search")
  Call<ResponseBody> getApplicants(@Query("type") String type, @Body RequestBodyModel body);

  @GET("/api/applicants/{applicantId}")
  Call<ApplicantModel> getApplicantDetail(@Path("applicantId") int applicantID,
                                          @Query("fl") String fl);

  @POST("/api/applicants/{applicantId}/interviewSms")
  Call<ResponseBody> sendInterviewSms(@Path("applicantId") int applicantID);

  @GET("/api/applicants/{applicantId}/comments")
  Call<List<CommentModel>> getApplicantComments(@Path("applicantId") int applicantID);

  @POST("/api/applicants/{applicantId}/comments")
  Call<CommentModel> createApplicantComment(@Path("applicantId") int applicantID,
                                            @Body RequestBodyModel body);

  @POST("/api/applicants/{applicantId}/comments/{commentId}/interviewFollowUps")
  Call<ResponseBody> setApplicantFollowUp(@Path("applicantId") int applicantID,
                                          @Path("commentId") int commentID,
                                          @Body RequestBodyModel body);

  @GET("/api_not_available")
  @Streaming
  Call<ResponseBody> downloadApplicantCV(@Path("applicant_id") int applicantID,
                                         @Query("access_token") String accessToken,
                                         @Query("concat") boolean concat);

  @POST("/api/applicants/{applicant_id}/state")
  Call<ResponseBody> setApplicantState(@Path("applicant_id") int applicant_id,
                                       @Body ChangeStateModel changeStateModel);

  @GET("/api/search")
  Call<ResponseBody> getFacetItems(@Query("facetName") String facet,
                                   @Query("fieldName") String field,
                                   @Query("searchText") String search,
                                   @Query("type_s") String type_s, @Query("type") String type,
                                   @Query("limit") int limit, @Query("offset") int offset);
}
