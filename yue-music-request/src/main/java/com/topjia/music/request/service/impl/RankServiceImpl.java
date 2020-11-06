package com.topjia.music.request.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topjia.music.request.entity.singer.Singer;
import com.topjia.music.request.entity.song.Song;
import com.topjia.music.request.entity.top.*;
import com.topjia.music.request.service.RankService;
import com.topjia.music.request.util.HandleReqData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author wjh
 * @date 2020-05-24 18:02
 */
@Service
@Slf4j
public class RankServiceImpl implements RankService {
    @Override
    public List<TopGroup> getTopListInfo(String reqData) throws Exception {
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData);
        // 处理排行榜数据
        JSONArray jsonArray = response.getJSONObject("toplist").getJSONObject("data").getJSONArray("group");
        ArrayList<TopGroup> topGroups = new ArrayList<>();
        jsonArray.forEach((o) -> {
            JSONObject jsonTopGroup = (JSONObject) o;
            TopGroup topGroup = new TopGroup();
            topGroup.setGroupId(jsonTopGroup.getString("groupId"));
            topGroup.setGroupName(jsonTopGroup.getString("groupName"));
            JSONArray toplist = jsonTopGroup.getJSONArray("toplist");
            ArrayList<Top> tops = new ArrayList<>();
            toplist.forEach((topObj) -> {
                JSONObject jsonTop = (JSONObject) topObj;
                if (!jsonTop.getInteger("topId").equals(201)) {
                    Top top = new Top();
                    top.setPeriod(jsonTop.getString("period"));
                    top.setHeadPicUrl(jsonTop.getString("headPicUrl"));
                    top.setFrontPicUrl(jsonTop.getString("frontPicUrl"));
                    top.setTopId(jsonTop.getInteger("topId"));
                    top.setTopTitle(jsonTop.getString("title"));
                    top.setUpdateTips(jsonTop.getString("updateTips"));
                    top.setTopHistory(jsonTop.getJSONObject("history"));
                    top.setListenNum(jsonTop.getInteger("listenNum"));
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
                    tops.add(top);
                    topGroup.setTopList(tops);
                }
            });
            topGroups.add(topGroup);
        });
        return topGroups;
    }

    @Override
    public Map<String, Object> getTopDetail(String reqData) throws Exception {
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData);
        JSONObject topDetailObj = response.getJSONObject("detail").getJSONObject("data");
        JSONObject jsonTop = topDetailObj.getJSONObject("data");
        HashMap<String, Object> res = new HashMap<>();
        // 处理排行耪详细信息
        Top top = new Top();
        top.setPeriod(jsonTop.getString("period"));
        top.setHeadPicUrl(jsonTop.getString("headPicUrl"));
        top.setFrontPicUrl(jsonTop.getString("frontPicUrl"));
        top.setTopId(jsonTop.getInteger("topId"));
        top.setTopTitle(jsonTop.getString("title"));
        top.setUpdateTips(jsonTop.getString("updateTips"));
        top.setTopHistory(jsonTop.getJSONObject("history"));
        top.setUpdateTime(jsonTop.getString("updateTime"));
        top.setSong(jsonTop.getJSONArray("song"));
        top.setTitleDetail(jsonTop.getString("titleDetail"));
        top.setTitleShare(jsonTop.getString("titleShare"));
        top.setIntro(jsonTop.getString("intro"));
        top.setListenNum(jsonTop.getInteger("listenNum"));
        res.put("topDetail", top);
        // 处理排行榜歌曲
        JSONArray detailObjJSONObject = topDetailObj.getJSONArray("songInfoList");
        ArrayList<Song> songs = new ArrayList<>();
        for (Object o1 : detailObjJSONObject) {
            JSONObject songObj = (JSONObject) o1;
            Song song = new Song();
            song.setId(songObj.getString("id"));
            song.setMid(songObj.getString("mid"));
            JSONArray singers = songObj.getJSONArray("singer");
            ArrayList<Singer> singerArrayList = new ArrayList<>();
            for (Object singero : singers) {
                JSONObject singerObj = (JSONObject) singero;
                Singer singer = new Singer();
                singer.setSingerId(singerObj.getInteger("id"));
                singer.setSingerMid(singerObj.getString("mid"));
                singer.setSingerName(singerObj.getString("name"));
                singer.setSingerPic("https://y.gtimg.cn/music/photo_new/T001R300x300M000" + singerObj.getString("mid") + ".jpg?max_age=2592000");
                singerArrayList.add(singer);
            }
            song.setSingers(singerArrayList);
            song.setName(songObj.getString("name"));
            String album = songObj.getJSONObject("album").getString("name");
            if (!StringUtils.isEmpty(album)) {
                song.setAlbum(album);
                song.setImage("https://y.gtimg.cn/music/photo_new/T002R500x500M000" + songObj.getJSONObject("album").getString("mid") + ".jpg?max_age=2592000");
            }
            song.setDuration(songObj.getString("interval"));
            song.setTitle(songObj.getString("title"));
            song.setSubTitle(songObj.getString("subtitle"));
            songs.add(song);
        }
        res.put("top_song_list", songs);
        return res;
    }
}
