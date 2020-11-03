package com.topjia.music.request.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topjia.music.request.entity.FocusImage;
import com.topjia.music.request.entity.disst.Disst;
import com.topjia.music.request.entity.singer.Singer;
import com.topjia.music.request.entity.song.Song;
import com.topjia.music.request.service.RecommendBaseService;
import com.topjia.music.request.util.HandleReqData;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author wjh
 * @date 2020-05-24 16:26
 */
@Service
public class RecommendBaseServiceImpl implements RecommendBaseService {

    @Override
    public Object getRecommendFocus(String reqData) throws Exception {
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData);
        HashMap<String, Object> result = new HashMap<>();
        JSONObject niche = (JSONObject) response.getJSONObject("focus").getJSONObject("data").getJSONObject("shelf").getJSONArray("v_niche").get(0);
        JSONArray focusArray = niche.getJSONArray("v_card");
        ArrayList<FocusImage> recommendPics = new ArrayList<>();
        for (int i = 0; i < focusArray.size(); i++) {
            JSONObject obj = (JSONObject) focusArray.get(i);
            Integer jumptype = obj.getInteger("jumptype");
            String jump_info = "";
            if (jumptype == 10014) {
                jump_info = "https://y.qq.com/n/yqq/playlist/" + obj.getString("id") + ".html";
            }
            if (jumptype == 10002) {
                jump_info = "https://y.qq.com/n/yqq/album/" + obj.getString("subid") + ".html";
            }
            if (jumptype == 3002) {
                jump_info = obj.getString("id");
            }
            // 重新封装结果返回给前端
            FocusImage recommendPic = new FocusImage();
            recommendPic.setJumpInfo(jump_info);
            String pic_info = obj.getString("cover");
            recommendPic.setPicInfo(pic_info);
            recommendPics.add(recommendPic);
        }
        result.put("focus", recommendPics);
        return result;
    }

    @Override
    public Object getRecommendPlayListAndNewMusic(String reqData) throws Exception {
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData);
        // 处理合并数据
        HashMap<String, Object> res = new HashMap<>();
        // 处理推荐歌单列表
        JSONArray recomDisstlistArray = response.getJSONObject("recomPlaylist").getJSONObject("data").getJSONArray("v_hot");
        ArrayList<Disst> dissts = new ArrayList<>();
        for (Object o1 : recomDisstlistArray) {
            Disst disst = new Disst();
            JSONObject dissObj = (JSONObject) o1;
            disst.setDisstId(dissObj.getString("content_id"));
            disst.setDisstLogo(dissObj.getString("cover"));
            disst.setNickname(dissObj.getString("username"));
            disst.setVisitnum(dissObj.getInteger("listen_num"));
            disst.setDisstName(dissObj.getString("title"));
            dissts.add(disst);
        }
        res.put("playlist", dissts);
        //处理歌曲
        ArrayList<Song> songs = getSongs(response, res);
        res.put("newSongList", songs);
        return res;
    }

    @Override
    public Object getNewSong(String reqData) throws Exception {
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData);
        // 处理合并数据
        HashMap<String, Object> res = new HashMap<>();
        //处理歌曲
        ArrayList<Song> songs = getSongs(response, res);
        res.put("newSongList", songs);
        return res;
    }

    private ArrayList<Song> getSongs(JSONObject response, HashMap<String, Object> res) {
        res.put("lanlist", response.getJSONObject("new_song").getJSONObject("data").getJSONArray("lanlist"));
        JSONArray songlist = response.getJSONObject("new_song").getJSONObject("data").getJSONArray("songlist");
        ArrayList<Song> songs = new ArrayList<>();
        for (Object o1 : songlist) {
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
        return songs;
    }
}
