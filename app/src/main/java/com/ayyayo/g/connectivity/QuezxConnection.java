package com.ayyayo.g.connectivity;

import android.util.Log;
import java.lang.String;
import com.google.common.base.Joiner;
import com.google.gson.reflect.TypeToken;
import com.ayyayo.g.common.FileHelper;
import com.ayyayo.g.common.JsonConverter;
import com.ayyayo.g.common.SharedPreferencesUtility;
import com.ayyayo.g.common.constants.Random;
import com.ayyayo.g.common.constants.ServerConstant;
import com.ayyayo.g.listener.ResultCallBack;
import com.ayyayo.g.model.AccessToken;
import com.ayyayo.g.model.ApplicantModel;
import com.ayyayo.g.model.ChangeStateModel;
import com.ayyayo.g.model.CommentModel;
import com.ayyayo.g.model.FilterModel;
import com.ayyayo.g.model.JobFollowerModel;
import com.ayyayo.g.model.JobListModel;
import com.ayyayo.g.model.JobModel;
import com.ayyayo.g.model.JobStateChangeModel;
import com.ayyayo.g.model.ReasonModel;
import com.ayyayo.g.model.RequestBodyModel;
import com.ayyayo.g.model.ResponseModel;
import com.ayyayo.g.model.StateModel;
import com.ayyayo.g.model.StatusModel;
import com.ayyayo.g.model.UserModel;
import com.ayyayo.g.model.inner.SolarRequest;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class QuezxConnection {
  private RetroFitConnector retroFitConnector;
  private SharedPreferencesUtility sharedPreferencesUtility;
  private JsonConverter jsonConverter;

  public QuezxConnection(RetroFitConnector retroFitConnector, JsonConverter jsonConverter,
                         SharedPreferencesUtility sharedPreferencesUtility) {
    this.retroFitConnector = retroFitConnector;
    this.sharedPreferencesUtility = sharedPreferencesUtility;
    this.jsonConverter = jsonConverter;
  }

  public void loginUser(final String mobile, final ResultCallBack<UserModel> resultCallBack) {
    QuezxService service = retroFitConnector.createLoginService(QuezxService.class);
    Call<AccessToken> request = service.loginUser(mobile);
    request.enqueue(new Callback<AccessToken>() {
      @Override
      public void onResponse(Response<AccessToken> response, Retrofit retrofit) {
        if (response.isSuccess()) {

          Log.e("user success", "user success");
          sharedPreferencesUtility.setAccessToken(response.body());
          getUserInfo(new ResultCallBack<UserModel>() {
            @Override
            public void onResultCallBack(UserModel user, Exception e) {
              resultCallBack.onResultCallBack(user, null);
            }
          });
        } else {
          Log.e("user failed", "user ");
          resultCallBack.onResultCallBack(null, null);
        }
      }

      @Override
      public void onFailure(Throwable t) {
        t.printStackTrace(System.err);
        resultCallBack.onResultCallBack(null, new Exception());
      }
    });
  }

  public void getUserInfo(final ResultCallBack<UserModel> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }

        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        Call<UserModel> request = service.getUserInfo();
        request.enqueue(new Callback<UserModel>() {
          @Override
          public void onResponse(Response<UserModel> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              UserModel user = response.body();
              sharedPreferencesUtility.setCurrentUser(user);
              resultCallBack.onResultCallBack(user, null);
            } else {
              resultCallBack.onResultCallBack(null, new Exception());
            }
          }

          @Override
          public void onFailure(Throwable t) {
            t.printStackTrace(System.err);
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void getAccessToken(final ResultCallBack<AccessToken> resultCallBack) {
    AccessToken accessToken = sharedPreferencesUtility.getAccessToken();
    if (accessToken.isValid()) {
      resultCallBack.onResultCallBack(accessToken, null);
      return;
    }
    refreshAccessToken(accessToken, resultCallBack);
  }

  public void refreshAccessToken(AccessToken accessToken, final ResultCallBack<AccessToken> resultCallBack) {
    QuezxService service = retroFitConnector.createLoginService(QuezxService.class);
    Call<AccessToken> request = service.refreshToken("refresh_token", accessToken.refresh_token);
    request.enqueue(new Callback<AccessToken>() {
      @Override
      public void onResponse(Response<AccessToken> response, Retrofit retrofit) {
        if (response.isSuccess()) {
          sharedPreferencesUtility.setAccessToken(response.body());
          resultCallBack.onResultCallBack(response.body(), null);
        } else {
          resultCallBack.onResultCallBack(null, new Exception());
        }
      }

      @Override
      public void onFailure(Throwable t) {
        t.printStackTrace(System.err);
        resultCallBack.onResultCallBack(null, new Exception());
      }
    });
  }

  public void getReasons(final ResultCallBack<List<ReasonModel>> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }

        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        Call<List<ReasonModel>> request = service.getReasons();
        request.enqueue(new Callback<List<ReasonModel>>() {
          @Override
          public void onResponse(Response<List<ReasonModel>> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              resultCallBack.onResultCallBack(response.body(), null);
            } else {
              resultCallBack.onResultCallBack(null, new Exception());
            }
          }

          @Override
          public void onFailure(Throwable t) {
            t.printStackTrace(System.err);
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void getAllPossibleState(final ResultCallBack<List<StateModel>> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }

        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        Call<List<StateModel>> request = service.getStates();
        request.enqueue(new Callback<List<StateModel>>() {
          @Override
          public void onResponse(Response<List<StateModel>> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              resultCallBack.onResultCallBack(response.body(), null);
            } else {
              resultCallBack.onResultCallBack(null, new Exception());
            }
          }

          @Override
          public void onFailure(Throwable t) {
            t.printStackTrace(System.err);
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void logout(final ResultCallBack<Boolean> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }

        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        Call<ResponseBody> request = service.logout(accessToken);
        request.enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              if (resultCallBack != null)
                resultCallBack.onResultCallBack(true, null);
            } else {
              if (resultCallBack != null)
                resultCallBack.onResultCallBack(null, new Exception());
            }
          }

          @Override
          public void onFailure(Throwable t) {
            t.printStackTrace(System.err);
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void getJobList(final int page, final String status, final ResultCallBack<List<JobListModel>> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }

        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        Call<ResponseBody> request = service.getJobs(ServerConstant.DEFAULT_PAGE_SIZE,
            page * Random.DEFAULT_PAGE_SIZE, status, "");
        request.enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              String responseString = "";
              try {
                responseString = response.body().string();
                JSONObject rootJsonObject = new JSONObject(responseString);
                responseString = rootJsonObject.getString("jobs");
              } catch (Exception e) {
              }
              List<JobListModel> jobListModelList = jsonConverter.fromJson(responseString,
                  new TypeToken<List<JobListModel>>() {
                  }.getType());
              resultCallBack.onResultCallBack(jobListModelList, null);
            } else {
              resultCallBack.onResultCallBack(null, new Exception());
            }
          }

          @Override
          public void onFailure(Throwable t) {
            t.printStackTrace(System.err);
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void getJobDetail(final int job_id, final ResultCallBack<JobModel> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }

        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        Call<JobModel> request = service.getJobDetail(job_id);
        request.enqueue(new Callback<JobModel>() {
          @Override
          public void onResponse(Response<JobModel> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              resultCallBack.onResultCallBack(response.body(), null);
            } else {
              resultCallBack.onResultCallBack(null, new Exception());
            }
          }

          @Override
          public void onFailure(Throwable t) {
            t.printStackTrace(System.err);
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void updateJobStatus(final int job_id, final JobStateChangeModel changeModel, final ResultCallBack<Boolean> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }

        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        Call<ResponseBody> request = service.updateJobStatus(job_id, changeModel);
        request.enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              resultCallBack.onResultCallBack(true, null);
            } else {
              resultCallBack.onResultCallBack(false, new Exception());
            }
          }

          @Override
          public void onFailure(Throwable t) {
            t.printStackTrace(System.err);
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void getJobApplicants(final int jobID, final String state, final int page, final ResultCallBack<List<StatusModel>> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }

        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        Call<List<StatusModel>> request = service.getJobApplicants(jobID, state,
            page * Random.DEFAULT_PAGE_SIZE, Random.DEFAULT_PAGE_SIZE);
        request.enqueue(new Callback<List<StatusModel>>() {
          @Override
          public void onResponse(Response<List<StatusModel>> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              resultCallBack.onResultCallBack(response.body(), null);
            } else {
              resultCallBack.onResultCallBack(null, new Exception());
            }
          }

          @Override
          public void onFailure(Throwable t) {
            t.printStackTrace(System.err);
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void getApplicants(final int page, final List<FilterModel> filterList, final String nameNumber,
                            final ResultCallBack<ResponseModel<ApplicantModel>> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }

        RequestBodyModel body = new RequestBodyModel();
        body.q = "{!child of=\"type_s:job\"}type_s:(*job*)";
        body.rows = Random.DEFAULT_PAGE_SIZE;
        body.start = page * Random.DEFAULT_PAGE_SIZE;

        body.params = new SolarRequest();
        body.params.sort = "role desc";
        for (FilterModel f : filterList) {
          switch (f.id) {
            case Random.STATUS:
              if (f.selected.size() > 0) {
                if (body.params.fq.length() > 0) {
                  body.params.fq += " AND ";
                }
                body.params.fq += "state_id:(" + Joiner.on(" OR ").join(f.selected) + ")";
              }
              break;
            case Random.INTERVIEW_TIME:
              if (f.selected.size() > 0) {
                if (body.params.fq.length() > 0) {
                  body.params.fq += " AND ";
                }
                body.params.fq += "interview_time:[" + f.selected.get(0) + " TO " + f.selected.get(1) + "]";
                body.params.sort = "interview_time asc, id desc";
              }
              break;
            case Random.CLIENT_EM:
              if (f.selected.size() > 0) {
                if (body.q.length() > 0) {
                  body.q += " AND ";
                }
                body.q += "eng_mgr_name_sf:(" + Joiner.on(" OR ").join(f.selected) + ")";
              }
              break;
            case Random.CLIENT_NAME:
              if (f.selected.size() > 0) {
                if (body.q.length() > 0) {
                  body.q += " AND ";
                }
                body.q += "client_name_sf:(" + Joiner.on(" OR ").join(f.selected) + ")";
              }
              break;
            case Random.CONSULTANT_EM:
              if (f.selected.size() > 0) {
                if (body.params.fq.length() > 0) {
                  body.params.fq += " AND ";
                }
                body.params.fq += "eng_mgr_name_sf:(" + Joiner.on(" OR ").join(f.selected) + ")";
              }
              break;
            case Random.CONSULTANT:
              if (f.selected.size() > 0) {
                if (body.params.fq.length() > 0) {
                  body.params.fq += " AND ";
                }
                body.params.fq += "client_name_sf:(" + Joiner.on(" OR ").join(f.selected) + ")";
              }
              break;
          }
        }
        if (nameNumber.length() > 0) {
          if (body.params.fq.length() > 0) {
            body.params.fq += " AND ";
          }
          if (nameNumber.length() == 10 && nameNumber.matches("\\d+")) {
            body.params.fq += String.format("mobile:%s", nameNumber);
          } else {
            body.params.fq += String.format("name:\"*%s*\"", nameNumber);
          }
        }
        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        Call<ResponseBody> request = service.getApplicants("applicantStatusSolr", body);
        request.enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              String responseString = "";
              if (response.body() != null) {
                try {
                  responseString = response.body().string();
                  JSONObject rootJsonObject = new JSONObject(responseString);
                  responseString = rootJsonObject.getJSONObject("response").toString();
                } catch (Exception e) {
                }
              }
              ResponseModel<ApplicantModel> applicants = jsonConverter.fromJson(responseString,
                  new TypeToken<ResponseModel<ApplicantModel>>() {
                  }.getType());
              resultCallBack.onResultCallBack(applicants, null);
            } else {
              resultCallBack.onResultCallBack(null, new Exception());
            }
          }

          @Override
          public void onFailure(Throwable t) {
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void getApplicantDetail(final int applicantId,
                                 final ResultCallBack<ApplicantModel> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }
        String fl = "_root_,name,id,applicant_score,state_name,state_id,email,total_exp,skills," +
            "edu_degree,exp_salary,exp_designation,exp_employer,email,notice_period,mobile," +
            "exp_location,expected_ctc";
        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        Call<ApplicantModel> request = service.getApplicantDetail(applicantId, fl);
        request.enqueue(new Callback<ApplicantModel>() {
          @Override
          public void onResponse(Response<ApplicantModel> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              resultCallBack.onResultCallBack(response.body(), null);
            } else {
              resultCallBack.onResultCallBack(null, new Exception());
            }
          }

          @Override
          public void onFailure(Throwable t) {
            t.printStackTrace(System.err);
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void getApplicantComments(final int applicantId, final ResultCallBack<List<CommentModel>> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }

        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        Call<List<CommentModel>> request = service.getApplicantComments(applicantId);
        request.enqueue(new Callback<List<CommentModel>>() {
          @Override
          public void onResponse(Response<List<CommentModel>> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              resultCallBack.onResultCallBack(response.body(), null);
            } else {
              resultCallBack.onResultCallBack(null, new Exception());
            }
          }

          @Override
          public void onFailure(Throwable t) {
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void downloadCV(final ApplicantModel applicantModel, final boolean concat, final ResultCallBack<File> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }

        QuezxService service = retroFitConnector.createServiceNoAuthentication(QuezxService.class);
        Call<ResponseBody> request = service.downloadApplicantCV(applicantModel.id,
            accessToken.access_token, concat);

        request.enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              String fileName = applicantModel.name + "-resume.pdf";
              try {
                InputStream input = response.body().byteStream();
                new FileHelper().copyInputStreamToFile(input, fileName, resultCallBack);
                //  rest of your code
              } catch (Exception e) {
              }
            } else {
              resultCallBack.onResultCallBack(null, new Exception());
            }
          }

          @Override
          public void onFailure(Throwable t) {
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void changeApplicantState(final int applicant_id, final ChangeStateModel changeStateModel,
                                   final ResultCallBack<Integer> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }
        if (changeStateModel.comments != null && changeStateModel.comments.length() == 0)
          changeStateModel.comments = null;
        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        Call<ResponseBody> request = service.setApplicantState(applicant_id, changeStateModel);

        request.enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              resultCallBack.onResultCallBack(changeStateModel.state_id, null);
            } else {
              resultCallBack.onResultCallBack(null, new Exception());
            }
          }

          @Override
          public void onFailure(Throwable t) {
            t.printStackTrace(System.err);
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void setApplicantFollowUps(final int applicantId, final int commentId, final int followUpId,
                                    final ResultCallBack<Boolean> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }

        RequestBodyModel body = new RequestBodyModel();
        body.followUpOptionId = followUpId;
        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        final Call<ResponseBody> request = service.setApplicantFollowUp(applicantId, commentId, body);
        request.enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              if (resultCallBack != null)
                resultCallBack.onResultCallBack(true, null);
            } else {
              if (resultCallBack != null)
                resultCallBack.onResultCallBack(false, new Exception());
            }
          }

          @Override
          public void onFailure(Throwable t) {
            if (resultCallBack != null)
              resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void getFilterItems(final int page, final String facet, final String field, final String searchText,
                             final String type_s, final String type,
                             final ResultCallBack<List<String>> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }

        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        Call<ResponseBody> request = service.getFacetItems(facet, field, searchText, type_s, type,
            ServerConstant.DEFAULT_PAGE_SIZE, page * Random.DEFAULT_PAGE_SIZE);
        request.enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              String responseString = "[]";
              if (response.body() != null) {
                try {
                  JSONObject rootJsonObject = new JSONObject(response.body().string());
                  responseString = rootJsonObject.get(facet).toString();
                } catch (Exception e) {
                }
              }
              List<String> itemValue = jsonConverter.fromJson(responseString,
                  new TypeToken<List<String>>() {
                  }.getType());
              resultCallBack.onResultCallBack(itemValue, null);
            } else {
              resultCallBack.onResultCallBack(null, new Exception());
            }
          }

          @Override
          public void onFailure(Throwable t) {
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void createComment(final int applicantId, final String message,
                            final ResultCallBack<CommentModel> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }
        final RequestBodyModel body = new RequestBodyModel();
        body.comment = message;
        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        final Call<CommentModel> request = service.createApplicantComment(applicantId, body);
        request.enqueue(new Callback<CommentModel>() {
          @Override
          public void onResponse(Response<CommentModel> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              CommentModel comment = response.body();
              UserModel user = sharedPreferencesUtility.getCurrentUser();
              comment.body = message;
              comment.user = user;
              comment.user_id = user.id;
              comment.created_at = Calendar.getInstance();
              resultCallBack.onResultCallBack(comment, null);
            } else {
              resultCallBack.onResultCallBack(null, null);
            }
          }

          @Override
          public void onFailure(Throwable t) {
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void getJobFollowers(final int jobId,
                              final ResultCallBack<List<JobFollowerModel>> resultCallBack) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          resultCallBack.onResultCallBack(null, e);
          return;
        }
        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        final Call<List<JobFollowerModel>> request = service.getJobFollowers(jobId);
        request.enqueue(new Callback<List<JobFollowerModel>>() {
          @Override
          public void onResponse(Response<List<JobFollowerModel>> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              resultCallBack.onResultCallBack(response.body(), null);
            } else {
              resultCallBack.onResultCallBack(null, null);
            }
          }

          @Override
          public void onFailure(Throwable t) {
            resultCallBack.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  public void sendInterviewSms(final int applicantId, final ResultCallBack<Boolean> callback) {
    getAccessToken(new ResultCallBack<AccessToken>() {
      @Override
      public void onResultCallBack(AccessToken accessToken, Exception e) {
        if (e != null) {
          callback.onResultCallBack(null, e);
          return;
        }
        QuezxService service = retroFitConnector.createService(QuezxService.class, accessToken);
        final Call<ResponseBody> request = service.sendInterviewSms(applicantId);
        request.enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
            if (response.isSuccess()) {
              callback.onResultCallBack(true, null);
            } else {
              callback.onResultCallBack(false, null);
            }
          }

          @Override
          public void onFailure(Throwable t) {
            callback.onResultCallBack(null, new Exception());
          }
        });
      }
    });
  }

  // TODO logout call
  public void logoutUser(ResultCallBack<Boolean> logoutResultCallback) {

  }
}