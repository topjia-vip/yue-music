package com.topjia.music.request.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topjia.music.request.entity.disst.Disst;
import com.topjia.music.request.entity.disst.Tag;
import com.topjia.music.request.entity.disst_category_group.DisstCategory;
import com.topjia.music.request.entity.disst_category_group.DisstCategoryGroup;
import com.topjia.music.request.entity.singer.Singer;
import com.topjia.music.request.entity.song.Song;
import com.topjia.music.request.service.DisstService;
import com.topjia.music.request.util.HandleReqData;
import com.topjia.music.request.util.RequestHeader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author wjh
 * @date 2020-05-24 17:19
 */
@Service
public class DisstServiceImpl implements DisstService {

    @Override
    public List<DisstCategoryGroup> getDisstCategoryGroup(String reqData) throws Exception {
        RequestHeader header = new RequestHeader("c.y.qq.com", "https://y.qq.com/portal/playlist.html", "");
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData, header);
        JSONArray categories = response.getJSONObject("data").getJSONArray("categories");
        ArrayList<DisstCategoryGroup> disstCategoryGroups = new ArrayList<>();
        categories.forEach(groupObj -> {
            JSONObject group = (JSONObject) groupObj;
            DisstCategoryGroup disstCategoryGroup = new DisstCategoryGroup();
            disstCategoryGroup.setCategoryGroupName(group.getString("categoryGroupName"));
            JSONArray items = group.getJSONArray("items");
            ArrayList<DisstCategory> disstCategories = new ArrayList<>();
            items.forEach(categoryObj -> {
                JSONObject category = (JSONObject) categoryObj;
                DisstCategory disstCategory = new DisstCategory();
                disstCategory.setCategoryId(category.getInteger("categoryId"));
                disstCategory.setCategoryName(category.getString("categoryName"));
                disstCategories.add(disstCategory);
            });
            disstCategoryGroup.setCategories(disstCategories);
            disstCategoryGroups.add(disstCategoryGroup);
        });
        return disstCategoryGroups;
    }

    @Override
    public Map<String, Object> getDisstList(String reqData) throws Exception {
        RequestHeader header = new RequestHeader("c.y.qq.com", "https://c.y.qq.com/", "");
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData, header);
        HashMap<String, Object> res = new HashMap<>();
        res.put("disstSum", response.getJSONObject("data").getInteger("sum"));
        JSONArray dissts = response.getJSONObject("data").getJSONArray("list");
        ArrayList<Disst> dissts1 = new ArrayList<>();
        dissts.forEach(disstObj -> {
            JSONObject disstJSON = (JSONObject) disstObj;
            Disst disst = new Disst();
            disst.setDisstId(disstJSON.getString("dissid"));
            disst.setDisstLogo(disstJSON.getString("imgurl"));
            disst.setDisstName(disstJSON.getString("dissname"));
            disst.setNickname(disstJSON.getJSONObject("creator").getString("name"));
            disst.setVisitnum(disstJSON.getInteger("listennum"));
            dissts1.add(disst);
        });
        res.put("dissts", dissts1);
        return res;
    }

    @Override
    public Disst getDisstDetail(String reqData) throws Exception {
        RequestHeader header = new RequestHeader("c.y.qq.com", "https://c.y.qq.com/", "");
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData, header);
        JSONObject cdlist = (JSONObject) response.getJSONArray("cdlist").get(0);
        Disst disst = new Disst();
        disst.setDisstId(cdlist.getString("disstid"));
        disst.setDisstName(cdlist.getString("dissname"));
        disst.setVisitnum(cdlist.getInteger("visitnum"));
        disst.setNickname(cdlist.getString("nickname"));
        disst.setDisstLogo(cdlist.getString("logo"));
        disst.setDesc(cdlist.getString("desc"));
        disst.setHeadUrl(cdlist.getString("headurl"));
        disst.setEncrypt_uin(cdlist.getString("encrypt_uin"));
        disst.setSongNum(cdlist.getInteger("songnum"));
        JSONArray tags = cdlist.getJSONArray("tags");
        ArrayList<Tag> tagArrayList = new ArrayList<>();
        tags.forEach(tagObj -> {
            JSONObject tagJSON = (JSONObject) tagObj;
            Tag tag = new Tag();
            tag.setName(tagJSON.getString("name"));
            tag.setId(tagJSON.getInteger("id"));
            tagArrayList.add(tag);
        });
        disst.setTags(tagArrayList);
        disst.setSonglist(getSongs(response));
        return disst;
    }

    @Override
    public List<Song> getDisstSongList(String reqData) throws Exception {
        RequestHeader header = new RequestHeader("c.y.qq.com", "https://c.y.qq.com/", "");
        JSONObject response = (JSONObject) HandleReqData.sendRequest(reqData, header);
        return getSongs(response);
    }

    private List<Song> getSongs(JSONObject getRes) {
        JSONObject cdlist = (JSONObject) getRes.getJSONArray("cdlist").get(0);
        JSONArray songlist = cdlist.getJSONArray("songlist");
        ArrayList<Song> songs = new ArrayList<>();
        for (Object o1 : songlist) {
            JSONObject songObj = (JSONObject) o1;
            Song song = new Song();
            song.setId(songObj.getString("id"));
            song.setMid(songObj.getString("mid"));
            song.setMediaMid(songObj.getJSONObject("file").getString("media_mid"));
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
            song.setSubTitle(songObj.getJSONObject("album").getString("subtitle"));
            songs.add(song);
        }
        return songs;
    }
}
