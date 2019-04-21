/*
 * Copyright 2018. techflowing
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.neuqer.android.mock.news;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 资讯列表数据mock
 *
 * @author techflowing
 * @since 2018/11/22 9:12 PM
 */
public class NewDataFlowMock {

    private static final int BOUND = 1000000;
    private List<String> mImgPool;
    private List<String> mSourcePool;
    private List<NewsType> mLayoutPool;
    private List<String> mTitlePool;
    private List<String> mDetailPool;

    public NewDataFlowMock() {
        mImgPool = new ArrayList<>();
        mSourcePool = new ArrayList<>();
        mLayoutPool = new ArrayList<>();
        mTitlePool = new ArrayList<>();
        mDetailPool = new ArrayList<>();
        init();
    }

    private void init() {
        mImgPool.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542902826950&di=30f066ef47728b1fad3491add196912e&imgtype=0&src=http%3A%2F%2Fimg000.hc360.cn%2Fy4%2FM02%2FE7%2F85%2FwKhQiFWLupSEJID0AAAAAIxaBYE598.jpg..210x210.jpg");
        mImgPool.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542902833668&di=c1457644e94f508ba9991b48d79c24c5&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D999475519%2C1729187542%26fm%3D214%26gp%3D0.jpg");
        mImgPool.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542902828961&di=949deb179ec3a9cbe7c8ac9ae1a67f47&imgtype=0&src=http%3A%2F%2Fimgs.shougongke.com%2FPublic%2Fdata%2Fhand%2F201509%2F21%2Fhost%2F144281512085647_228.jpg");
        mImgPool.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542902847705&di=d4ca2c269d31652def15ccd848a6e328&imgtype=0&src=http%3A%2F%2Fimg17.hc360.cn%2F17%2Fbusin%2F132%2F407%2Fb%2F17-132407027.jpg..210x210.jpg");
        mImgPool.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542902854634&di=ddf9842634a4c99d96799396a3d1fc38&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D3250482042%2C80056838%26fm%3D214%26gp%3D0.jpg");

        mSourcePool.add("宝石网");
        mSourcePool.add("珠宝网");
        mSourcePool.add("中华网");
        mSourcePool.add("珍宝网");

        mLayoutPool.add(NewsType.ONE_IMG_LEFT);
        mLayoutPool.add(NewsType.TEXT);
        mLayoutPool.add(NewsType.THREE_IMG);

        mTitlePool.add("【科普】听说南红包浆料很贵，那我得盘久点儿~");
        mTitlePool.add("【市场】香港珠宝展盘点回顾，以及未来一年行业风向");
        mTitlePool.add("泰国宝石学院GIT一行参观中国彩色宝石网线下体验中心");
        mTitlePool.add("【羊城晚报】珠宝收藏火热--2018年热点趋势分析");
        mTitlePool.add("目前考古发现人类最早的珠宝出现在旧石器时代，直到新石器时代真正意义上珠宝才出现");

        mDetailPool.add("http://www.baidu.com");
        mDetailPool.add("http://www.jd.com");
    }

    public String getMockData(int page, int size) {
        JSONObject jsonObject = new JSONObject();
        JSONArray itemArray = new JSONArray();
        Random random = new Random();
        try {
            for (int i = page * size; i < (page + 1) * size; i++) {
                JSONObject item = new JSONObject();
                NewsType type = mLayoutPool.get(random.nextInt(BOUND) % mLayoutPool.size());
                item.put("_id", "news_" + i);
                item.put("layout", type.getName());
                item.put("data", getItemData(i, type));
                itemArray.put(item);
            }
            jsonObject.put("items", itemArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    private JSONObject getItemData(int num, NewsType type) throws JSONException {
        Random random = new Random();
        JSONObject jsonObject = new JSONObject();
        switch (type) {
            case TEXT:
                jsonObject.put("title", num + ":" + mTitlePool.get(random.nextInt(BOUND) % mTitlePool.size()));
                jsonObject.put("source", mSourcePool.get(random.nextInt(BOUND) % mSourcePool.size()));
                jsonObject.put("cmd", getCmd(mDetailPool.get(random.nextInt(BOUND) % mDetailPool.size())));
                break;
            case ONE_IMG_LEFT:
                jsonObject.put("title", num + ":" + mTitlePool.get(random.nextInt(BOUND) % mTitlePool.size()));
                jsonObject.put("source", mSourcePool.get(random.nextInt(BOUND) % mSourcePool.size()));
                jsonObject.put("cmd", getCmd(mDetailPool.get(random.nextInt(BOUND) % mDetailPool.size())));
                jsonObject.put("img_url", mImgPool.get(random.nextInt(BOUND) % mImgPool.size()));
                break;
            case THREE_IMG:
                jsonObject.put("title", num + ":" + mTitlePool.get(random.nextInt(BOUND) % mTitlePool.size()));
                jsonObject.put("source", mSourcePool.get(random.nextInt(BOUND) % mSourcePool.size()));
                jsonObject.put("cmd", getCmd(mDetailPool.get(random.nextInt(BOUND) % mDetailPool.size())));
                jsonObject.put("left_img_url", mImgPool.get(random.nextInt(BOUND) % mImgPool.size()));
                jsonObject.put("mid_img_url", mImgPool.get(random.nextInt(BOUND) % mImgPool.size()));
                jsonObject.put("right_img_url", mImgPool.get(random.nextInt(BOUND) % mImgPool.size()));
                break;
        }
        return jsonObject;
    }

    private String getCmd(String link) {
        return String.format("yunxiang://news/details?params={\"url\":\"%s\"}", link);
    }
}
