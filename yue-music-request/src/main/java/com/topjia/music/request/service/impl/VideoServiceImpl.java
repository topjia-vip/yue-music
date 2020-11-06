package com.topjia.music.request.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topjia.music.request.entity.singer.Singer;
import com.topjia.music.request.entity.top.RankSong;
import com.topjia.music.request.entity.top.Top;
import com.topjia.music.request.entity.top.TopGroup;
import com.topjia.music.request.entity.video.Video;
import com.topjia.music.request.service.VideoService;
import com.topjia.music.request.util.HandleReqData;
import com.topjia.music.request.util.RequestHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author topjia
 * @date 2020-11-05 19:24
 */
@Service
@Slf4j
public class VideoServiceImpl implements VideoService {
    @Override
    public List<Video> getRecommendMV(String reqData) throws Exception {
        RequestHeader header = new RequestHeader("c.y.qq.com", "https://y.qq.com/", "");
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData, header);
        return handleRecommendMV(response);
    }

    @Override
    public Top getMVRankInfo(String reqData) throws Exception {
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData);
        // 处理排行榜数据
        JSONArray jsonArray = response.getJSONObject("toplist").getJSONObject("data").getJSONArray("group");
        Top top = new Top();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject o = (JSONObject) jsonArray.get(i);
            if (o.getInteger("groupId").equals(0)) {
                JSONArray topListJSONArray = o.getJSONArray("toplist");
                for (int j = 0; j < topListJSONArray.size(); j++) {
                    JSONObject jsonTop = (JSONObject) topListJSONArray.get(j);
                    if (jsonTop.getInteger("topId").equals(201)) {
                        top.setPeriod(jsonTop.getString("period"));
                        top.setHeadPicUrl(jsonTop.getString("headPicUrl"));
                        top.setFrontPicUrl(jsonTop.getString("frontPicUrl"));
                        top.setTopId(jsonTop.getInteger("topId"));
                        top.setTopTitle(jsonTop.getString("title"));
                        top.setUpdateTips(jsonTop.getString("updateTips"));
                        top.setTopHistory(jsonTop.getJSONObject("history"));
                        top.setListenNum(jsonTop.getInteger("listenNum"));
                        top.setIntro(jsonTop.getString("intro"));
                        JSONArray rankSongArr = jsonTop.getJSONArray("song");
                        ArrayList<RankSong> songs = new ArrayList<>();
                        rankSongArr.forEach((rankSongObj) -> {
                            JSONObject rankSongJson = (JSONObject) rankSongObj;
                            RankSong rankSong = new RankSong();
                            rankSong.setAlbumMid(rankSongJson.getString("albumMid"));
                            rankSong.setRank(rankSongJson.getInteger("rank"));
                            rankSong.setRankType(rankSongJson.getInteger("rankType"));
                            rankSong.setRankValue(rankSongJson.getString("rankValue"));
                            rankSong.setRecType(rankSongJson.getInteger("recType"));
                            rankSong.setSingerMid(rankSongJson.getString("singerMid"));
                            rankSong.setSingerName(rankSongJson.getString("singerName"));
                            rankSong.setSongId(rankSongJson.getInteger("songId"));
                            rankSong.setSongType(rankSongJson.getInteger("songType"));
                            rankSong.setTitle(rankSongJson.getString("title"));
                            songs.add(rankSong);
                        });
                        top.setRankSong(songs);
                        break;
                    }
                }
            }
        }
        return top;
    }

    @Override
    public List<Video> getMVRankData(String reqData) throws Exception {
        RequestHeader header = new RequestHeader("u.y.qq.com", "https://y.qq.com/", "");
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData, header);
        return handleMVRankData(response);
    }

    @Override
    public HashMap<String, Object> getVideoTags(String reqData) throws Exception {
        RequestHeader header = new RequestHeader("u.y.qq.com", "https://y.qq.com/", "");
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData, header);
        return handleTags(response);
    }

    @Override
    public List<Video> getVideos(String reqData) throws Exception {
        RequestHeader header = new RequestHeader("u.y.qq.com", "https://y.qq.com/", "");
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData, header);
        return handleVideos(response);
    }

    private List<Video> handleVideos(JSONObject response) {
        ArrayList<Video> videos = new ArrayList<>();
        JSONArray mvListJSONArr = response.getJSONObject("mv_list").getJSONObject("data").getJSONArray("list");
        mvListJSONArr.forEach((item) -> {
            JSONObject mvJSONObj = (JSONObject) item;
            ArrayList<Singer> singers = new ArrayList<>();
            JSONArray singersJSONArr = mvJSONObj.getJSONArray("singers");
            singersJSONArr.forEach((singerObj) -> {
                JSONObject singerJSONObj = (JSONObject) singerObj;
                Singer singer = new Singer();
                singer.setSingerId(singerJSONObj.getInteger("id"));
                singer.setSingerMid(singerJSONObj.getString("mid"));
                singer.setSingerName(singerJSONObj.getString("name"));
                singer.setSingerPic("https://y.gtimg.cn/music/photo_new/T001R300x300M000" + singerJSONObj.getString("mid") + ".jpg?max_age=2592000");
                singers.add(singer);
            });

            Video video = Video.builder()
                    .mvId(mvJSONObj.getInteger("mvid"))
                    .vid(mvJSONObj.getString("vid"))
                    .singers(singers)
                    .createTime(mvJSONObj.getString("pubdate"))
                    .listenNum(mvJSONObj.getInteger("playcnt"))
                    .mvPicUrl(mvJSONObj.getString("picurl"))
                    .mvTitle(mvJSONObj.getString("title"))
                    .duration(mvJSONObj.getInteger("duration"))
                    .build();
            videos.add(video);
        });
        return videos;
    }

    private HashMap<String, Object> handleTags(JSONObject response) {
        HashMap<String, Object> res = new HashMap<>();
        ArrayList<HashMap<String, Object>> areaList = new ArrayList<>();
        ArrayList<HashMap<String, Object>> versionList = new ArrayList<>();
        JSONArray areaJsonArr = response.getJSONObject("mv_tag").getJSONObject("data").getJSONArray("area");
        JSONArray versionJsonArr = response.getJSONObject("mv_tag").getJSONObject("data").getJSONArray("version");
        areaJsonArr.forEach((item) -> {
            HashMap<String, Object> area = new HashMap<>();
            JSONObject o = (JSONObject) item;
            area.put("id", o.getString("id"));
            area.put("name", o.getString("name"));
            areaList.add(area);
        });
        versionJsonArr.forEach((item) -> {
            HashMap<String, Object> version = new HashMap<>();
            JSONObject o = (JSONObject) item;
            version.put("id", o.getString("id"));
            version.put("name", o.getString("name"));
            versionList.add(version);
        });
        res.put("area", areaList);
        res.put("version", versionList);
        return res;
    }

    private List<Video> handleMVRankData(JSONObject response) {
        JSONArray rankListJsonArr = response.getJSONObject("request").getJSONObject("data").getJSONArray("rank_list");
        ArrayList<Video> videos = new ArrayList<>();
        rankListJsonArr.forEach((item) -> {
            JSONObject mvJsonObj = (JSONObject) item;
            ArrayList<Singer> singers = new ArrayList<>();
            JSONObject video_info = mvJsonObj.getJSONObject("video_info");
            JSONArray singersJSONArr = video_info.getJSONArray("singers");
            singersJSONArr.forEach((singerObj) -> {
                JSONObject singerJSONObj = (JSONObject) singerObj;
                Singer singer = new Singer();
                singer.setSingerId(singerJSONObj.getInteger("id"));
                singer.setSingerMid(singerJSONObj.getString("mid"));
                singer.setSingerName(singerJSONObj.getString("name"));
                singer.setSingerPic("https://y.gtimg.cn/music/photo_new/T001R300x300M000" + singerJSONObj.getString("mid") + ".jpg?max_age=2592000");
                singers.add(singer);
            });

            Video video = Video.builder()
                    .vid(video_info.getString("vid"))
                    .singers(singers)
                    .createTime(video_info.getString("pubdate"))
                    .mvPicUrl(video_info.getString("cover_pic"))
                    .mvTitle(video_info.getString("name"))
                    .build();
            videos.add(video);
        });
        return videos;
    }

    private List<Video> handleRecommendMV(JSONObject response) {
        JSONArray mvListJSONArr = response.getJSONObject("data").getJSONArray("mvlist");
        ArrayList<Video> videos = new ArrayList<>();
        mvListJSONArr.forEach((item) -> {
            JSONObject mvJSONObj = (JSONObject) item;
            ArrayList<Singer> singers = new ArrayList<>();
            JSONArray singersJSONArr = mvJSONObj.getJSONArray("singers");
            singersJSONArr.forEach((singerObj) -> {
                JSONObject singerJSONObj = (JSONObject) singerObj;
                Singer singer = new Singer();
                singer.setSingerId(singerJSONObj.getInteger("id"));
                singer.setSingerMid(singerJSONObj.getString("mid"));
                singer.setSingerName(singerJSONObj.getString("name"));
                singer.setSingerPic("https://y.gtimg.cn/music/photo_new/T001R300x300M000" + singerJSONObj.getString("mid") + ".jpg?max_age=2592000");
                singers.add(singer);
            });

            Video video = Video.builder()
                    .mvId(mvJSONObj.getInteger("mv_id"))
                    .vid(mvJSONObj.getString("vid"))
                    .singers(singers)
                    .createTime(mvJSONObj.getString("pub_date"))
                    .listenNum(mvJSONObj.getInteger("listennum"))
                    .mvDesc(mvJSONObj.getString("mvdesc"))
                    .mvPicUrl(mvJSONObj.getString("picurl"))
                    .mvTitle(mvJSONObj.getString("mvtitle"))
                    .build();
            videos.add(video);
        });
        return videos;
    }
}
