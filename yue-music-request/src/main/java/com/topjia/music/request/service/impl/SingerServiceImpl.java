package com.topjia.music.request.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topjia.music.request.entity.album.SingerAlbum;
import com.topjia.music.request.entity.singer.Singer;
import com.topjia.music.request.entity.singer.SingerTag;
import com.topjia.music.request.entity.song.Song;
import com.topjia.music.request.service.SingerService;
import com.topjia.music.request.util.HandleReqData;
import com.topjia.music.request.util.RequestHeader;
import org.dom4j.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author wjh
 * @date 2020-05-24 18:59
 */
@Service
public class SingerServiceImpl implements SingerService {
    @Override
    public HashMap<String, Object> getSingerList(String reqData) throws Exception {
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData);
        JSONObject jsonObject = response.getJSONObject("singerList").getJSONObject("data");
        // 处理标签
        HashMap<String, Object> res = new HashMap<>();
        JSONObject tags = jsonObject.getJSONObject("tags");
        handleSingerTag(tags, Arrays.asList("area", "genre", "index", "sex"), res);
        // 处理歌手列表
        JSONArray singerList = jsonObject.getJSONArray("singerlist");
        // 处理当前点击的是什么
        res.put("area", jsonObject.getString("area"));
        res.put("genre", jsonObject.getString("genre"));
        res.put("index", jsonObject.getString("index"));
        res.put("sex", jsonObject.getString("sex"));
        handleSingerList(singerList, res);
        // 处理总数据
        res.put("total", jsonObject.getInteger("total"));
        return res;
    }

    @Override
    public HashMap<String, Object> getSingerSongList(String reqData) throws Exception {
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData);
        HashMap<String, Object> songList = new HashMap<>();
        JSONArray jsonArray = response.getJSONObject("singerSongList").getJSONObject("data").getJSONArray("songList");
        songList.put("total", response.getJSONObject("singerSongList").getJSONObject("data").getInteger("totalNum"));
        ArrayList<Song> songs = new ArrayList<>();
        jsonArray.forEach(item -> {
            JSONObject songObj = (JSONObject) item;
            JSONObject songInfo = songObj.getJSONObject("songInfo");
            Song song = new Song();
            song.setId(songInfo.getString("id"));
            song.setMid(songInfo.getString("mid"));
            song.setName(songInfo.getString("name"));
            song.setMediaMid(songInfo.getJSONObject("file").getString("media_mid"));
            JSONArray singers = songInfo.getJSONArray("singer");
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
            String album = songInfo.getJSONObject("album").getString("name");
            if (!StringUtils.isEmpty(album)) {
                song.setAlbum(album);
                song.setImage("https://y.gtimg.cn/music/photo_new/T002R500x500M000" + songInfo.getJSONObject("album").getString("mid") + ".jpg?max_age=2592000");
            }
            song.setDuration(songInfo.getString("interval"));
            song.setTitle(songInfo.getString("title"));
            song.setSubTitle(songInfo.getString("subtitle"));
            songs.add(song);
        });
        songList.put("songs", songs);
        return songList;
    }

    @Override
    public HashMap<String, Object> getSingerAlbums(String reqData) throws Exception {
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData);
        JSONObject jsonObject = response.getJSONObject("getAlbumList").getJSONObject("data");
        HashMap<String, Object> res = new HashMap<>();
        res.put("total", jsonObject.getInteger("total"));
        JSONArray albumListJSON = jsonObject.getJSONArray("albumList");
        ArrayList<SingerAlbum> singerAlbums = new ArrayList<>();
        albumListJSON.forEach((item) -> {
            JSONObject object = (JSONObject) item;
            SingerAlbum singerAlbum = new SingerAlbum();
            singerAlbum.setAlbumID(object.getInteger("albumID"));
            singerAlbum.setAlbumMid(object.getString("albumMid"));
            singerAlbum.setAlbumName(object.getString("albumName"));
            singerAlbum.setAlbumTranName(object.getString("albumTranName"));
            singerAlbum.setImg("http://y.gtimg.cn/music/photo_new/T002R300x300M000" + object.getString("pmid") + ".jpg?max_age=2592000");
            singerAlbum.setPublishDate(object.getString("publishDate"));
            singerAlbum.setSingerName(object.getString("singerName"));
            singerAlbums.add(singerAlbum);
        });
        res.put("albums", singerAlbums);
        return res;
    }

    @Override
    public HashMap<String, Object> getSingerConcern(String reqData, String singerMid) throws Exception {
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData);
        HashMap<String, Object> concernNum = new HashMap<>();
        concernNum.put("concernNum", response.getJSONObject("concernNum").getJSONObject("data").getJSONObject("map_singer_num").getJSONObject(singerMid).getInteger("user_fansnum"));
        return concernNum;
    }

    @Override
    public HashMap<String, Object> getSingerInfo(String reqData) throws Exception {
        RequestHeader header = new RequestHeader("c.y.qq.com", "https://y.qq.com/", "");
        String xml = (String) HandleReqData.sendRequest(reqData, header);
        return resolveXML(xml);
    }

    private HashMap<String, Object> resolveXML(String xml) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = doc.getRootElement();// 指向根节点  <root>
        HashMap<String, Object> singerInfoMap = new HashMap<>();
        try {
            Element info = root.element("data").element("info");
            // 获取歌手简介
            String desc = info.element("desc").getText();
            if (desc != null) {
                singerInfoMap.put("desc", desc);
            }
            // 获取歌手基本信息
            List basicItems = info.element("basic").elements("item");
            List<HashMap<String, String>> baseInfo = new ArrayList<>();
            basicItems.forEach(item -> {
                Element itemEle = (Element) item;
                String key = itemEle.element("key").getText();
                String value = itemEle.element("value").getText();
                HashMap<String, String> map = new HashMap<>();
                map.put("key", key);
                map.put("value", key + ":" + value);
                baseInfo.add(map);
            });
            singerInfoMap.put("basic", baseInfo);
            // 获取其他信息
            Element other = info.element("other");
            if (other != null) {
                List otherItems = other.elements("item");
                ArrayList<HashMap<String, String>> otherInfo = new ArrayList<>();
                otherItems.forEach(item -> {
                    Element itemEle = (Element) item;
                    String key = itemEle.element("key").getText();
                    String value = itemEle.element("value").getText();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("key", key);
                    map.put("value", value);
                    otherInfo.add(map);
                });
                singerInfoMap.put("other", otherInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return singerInfoMap;
    }

    private void handleSingerTag(JSONObject tags, List<String> types, HashMap<String, Object> res) {
        HashMap<String, ArrayList<SingerTag>> map = new HashMap<>();
        for (String type : types) {
            ArrayList<SingerTag> singerTags = new ArrayList<>();
            tags.getJSONArray(type).forEach((item) -> {
                JSONObject i = (JSONObject) item;
                SingerTag singerTag = new SingerTag();
                singerTag.setTagId(i.getInteger("id"));
                singerTag.setTagName(i.getString("name"));
                singerTags.add(singerTag);
            });
            map.put(type, singerTags);
        }
        res.put("tags", map);
    }

    private void handleSingerList(JSONArray singerList, HashMap<String, Object> res) {
        ArrayList<Singer> singers = new ArrayList<>();
        singerList.forEach((singerObj) -> {
            JSONObject singerJsonObj = (JSONObject) singerObj;
            Singer singer = new Singer();
            singer.setSingerMid(singerJsonObj.getString("singer_mid"));
            singer.setSingerName(singerJsonObj.getString("singer_name"));
            String singer_pic = singerJsonObj.getString("singer_pic");
            if (!StringUtils.isEmpty(singer_pic)) {
                singer.setSingerPic("https://y.gtimg.cn/music/photo_new/T001R300x300M000" + singerJsonObj.getString("singer_mid") + ".jpg?max_age=2592000");
            }
            singer.setSingerId(singerJsonObj.getInteger("singer_id"));
            singers.add(singer);
        });
        res.put("singerList", singers);
    }
}
