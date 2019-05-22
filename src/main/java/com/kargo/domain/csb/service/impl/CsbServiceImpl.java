package com.kargo.domain.csb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kargo.common.enums.CsbEnum;
import com.kargo.common.util.BaseResponse;
import com.kargo.common.util.CheckArgsUtil;
import com.kargo.common.util.MyBeanUtil;
import com.kargo.common.util.UtilLocalDate;
import com.kargo.dao.*;
import com.kargo.domain.csb.req.*;
import com.kargo.domain.csb.resp.GoodsInfoAddCount;
import com.kargo.domain.csb.resp.MPOpenid;
import com.kargo.domain.csb.resp.OrderResp;
import com.kargo.domain.csb.resp.QueryOrdersByPhoneResp;
import com.kargo.domain.csb.service.CsbService;
import com.kargo.model.CodeOpenid;
import com.kargo.model.GoodsInfo;
import com.kargo.model.OrderGoodsInfo;
import com.kargo.model.Orders;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class CsbServiceImpl implements CsbService {

    private static final Logger logger = LoggerFactory.getLogger(CsbServiceImpl.class);

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired
    private OrderGoodsInfoMapper orderGoodsInfoMapper;
    @Autowired
    private OrdersMapper ordersMapper;


    @Autowired
    private RestTemplate restTemplate;

    @Value("${miniapp.appid}")
    String appId  ;
    @Value("${miniapp.secret}")
    String secret  ;


    @Autowired
    private CodeOpenidMapper codeOpenidMapper;




    @Override
    public String getOpenId(String code){
        logger.info("getOpenId code:[{}]",code);
        if(StringUtils.isBlank(code)){
            return "";
        }
        CodeOpenid codeOpenid = new CodeOpenid();
        codeOpenid.setCode(code);
        List<CodeOpenid> codeOpenids = codeOpenidMapper.selectPage(codeOpenid, PageRequest.of(0, 1));
        if(!CollectionUtils.isEmpty(codeOpenids)){
            return codeOpenids.get(0).getOpenid();
        }
        String url="https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";
        url = MessageFormat.format(url,appId,secret,code);
        String result = new RestTemplate().getForObject(url, String.class);
        logger.info("getOpenId code:[{}],result:[{}]",code,result);
        MPOpenid mpOpenid = JSONObject.parseObject(result, MPOpenid.class);
        //存放数据库
        if(mpOpenid ==null){
            return "";
        }
        if(StringUtils.isNotBlank(mpOpenid.getOpenid())){
            codeOpenid.setOpenid(mpOpenid.getOpenid());
            codeOpenidMapper.insertSelective(codeOpenid);
        }
        return mpOpenid.getOpenid();
    }


    @Override
    public BaseResponse cancelOrder(String orderNo){
        logger.info("cancelOrder req:[{}]",orderNo);
        BaseResponse baseResponse = new BaseResponse();
        int i = ordersMapper.cancelOrder(orderNo);
        return baseResponse.genSuccessResp(i);
    }

    @Override
    public BaseResponse queryOrdersByCode(String code, String orderStatus){
        logger.info("queryOrdersByCode code:[{}],orderStatus:[{}]",code,orderStatus);
        BaseResponse baseResponse = new BaseResponse();
        if(StringUtils.isBlank(code)){
            return baseResponse.genFail("请重新登陆");
        }
        if(StringUtils.isBlank(orderStatus)){
            return baseResponse.genFail("param error");
        }
        String openid = getOpenId(code);
        if(StringUtils.isBlank(openid)){
            return baseResponse.genFail("请重新登陆");
        }

        Orders orders = new Orders();
        orders.setOpenid(openid);
        orders.setOrderStatus(orderStatus);
        List<Orders> orderss = ordersMapper.selectPageOrder(orders, "update_time desc",null);
        if(CollectionUtils.isEmpty(orderss)){
            return baseResponse.genSuccessResp();
        }
        StringBuffer stringBuffer = null;
        OrderGoodsInfo orderGoodsInfo = null;
        List<QueryOrdersByPhoneResp> list = new ArrayList<>();
        QueryOrdersByPhoneResp resp = null;
        OrderResp orderResp = null;
        for(Orders tempOrders:orderss){
            resp = new QueryOrdersByPhoneResp();
            stringBuffer = new StringBuffer();
            orderGoodsInfo = new OrderGoodsInfo();
            orderGoodsInfo.setOrderNo(tempOrders.getOrderNo());
            List<OrderGoodsInfo> orderGoodsInfos = orderGoodsInfoMapper.selectPage(orderGoodsInfo, null);
            if(!CollectionUtils.isEmpty(orderGoodsInfos)){
                for(OrderGoodsInfo tempOrderGoodsInfo:orderGoodsInfos){
                    stringBuffer.append(tempOrderGoodsInfo.getGoodsName()).append("X").append(tempOrderGoodsInfo.getGoodsCount()).append(",");
                }
            }
            orderResp = new OrderResp();
            BeanUtils.copyProperties(tempOrders,orderResp);
            resp.setOrders(orderResp);
            resp.setOrderGoodsInfoList(orderGoodsInfos);
            resp.setOrderDetails(stringBuffer.toString().substring(0,stringBuffer.length()-1));
            list.add(resp);
        }
        return baseResponse.genSuccessResp(list);
    }


    @Override
    public BaseResponse queryOrdersByPhone(String phone, String orderStatus){
        logger.info("queryOrdersByPhone req:[{}]",phone);
        return new BaseResponse();
//        BaseResponse baseResponse = new BaseResponse();
//        if(StringUtils.isBlank(phone)){
//            return baseResponse.genFail("请输入手机号码");
//        }
//        if(StringUtils.isBlank(orderStatus)){
//            return baseResponse.genFail("param error");
//        }
//        Orders orders = new Orders();
//        orders.setPhone(phone);
//        orders.setOrderStatus(orderStatus);
//        List<Orders> orderss = ordersMapper.selectPageOrder(orders, "update_time desc",null);
//        if(CollectionUtils.isEmpty(orderss)){
//            return baseResponse.genSuccessResp();
//        }
//        StringBuffer stringBuffer = null;
//        OrderGoodsInfo orderGoodsInfo = null;
//        List<QueryOrdersByPhoneResp> list = new ArrayList<>();
//        QueryOrdersByPhoneResp resp = null;
//        for(Orders tempOrders:orderss){
//            resp = new QueryOrdersByPhoneResp();
//            stringBuffer = new StringBuffer();
//            orderGoodsInfo = new OrderGoodsInfo();
//            orderGoodsInfo.setOrderNo(tempOrders.getOrderNo());
//            List<OrderGoodsInfo> orderGoodsInfos = orderGoodsInfoMapper.selectPage(orderGoodsInfo, null);
//            if(!CollectionUtils.isEmpty(orderGoodsInfos)){
//                for(OrderGoodsInfo tempOrderGoodsInfo:orderGoodsInfos){
//                    stringBuffer.append(tempOrderGoodsInfo.getGoodsName()).append("X").append(tempOrderGoodsInfo.getGoodsCount()).append(",");
//                }
//            }
//            resp.setOrders(tempOrders);
//            resp.setOrderGoodsInfoList(orderGoodsInfos);
//            resp.setOrderDetails(stringBuffer.toString().substring(0,stringBuffer.length()-1));
//            list.add(resp);
//        }
//        return baseResponse.genSuccessResp(list);
    }





    @Override
    @Transactional
    public BaseResponse buy(BuyReq req){
        logger.info("buy req:[{}]",JSONObject.toJSONString(req));
        BaseResponse baseResponse = new BaseResponse();
        Map<String ,String> map= CheckArgsUtil.checkArgsMap(req);
        if (map!=null) {
            return baseResponse.genFail(map.values().iterator().next());
        }


        //根据code，查询openid
        req.setOpenid(getOpenId(req.getCode()));
        logger.info("buy final req:[{}]",JSONObject.toJSONString(req));
        if(StringUtils.isBlank(req.getOpenid())){
            return baseResponse.genFail("请重新登陆!");
        }

        Set<Integer> integers = req.getToBuyGoods().keySet();
        List<Integer> idList = new ArrayList<>();
        for(Integer id:integers){
            idList.add(id);
        }
        String orderNo =UtilLocalDate.getOrderNo("",2).substring(2);
        List<GoodsInfo> goodsInfos = goodsInfoMapper.selectByIds(idList);
        OrderGoodsInfo orderGoodsInfo = null;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(GoodsInfo tempGoodsInfo:goodsInfos){
            orderGoodsInfo = new OrderGoodsInfo();
            BeanUtils.copyProperties(tempGoodsInfo,orderGoodsInfo);
            orderGoodsInfo.setOrderNo(orderNo);
            orderGoodsInfo.setGoodsCount(req.getToBuyGoods().get(tempGoodsInfo.getId()));
            totalAmount=totalAmount.add(orderGoodsInfo.getPrice().multiply(new BigDecimal(orderGoodsInfo.getGoodsCount())));
            orderGoodsInfo.setId(null);
            orderGoodsInfoMapper.insertSelective(orderGoodsInfo);
        }
        Orders orders = new Orders();
        BeanUtils.copyProperties(req,orders);
        orders.setOrderNo(orderNo);
        orders.setTotalAmount(totalAmount);
        orders.setOrderStatus(CsbEnum.OrderStatusEnum.CREADTED.getValue());
        ordersMapper.insertSelective(orders);
        return baseResponse.genSuccessResp();
    }



    @Override
    public BaseResponse queryGoodsInfosList(){
        logger.info("queryGoodsInfosList ");
        BaseResponse baseResponse = new BaseResponse();
        List<GoodsInfo> goodsInfos = goodsInfoMapper.queryGoodsInfosList();
        if(CollectionUtils.isEmpty(goodsInfos)){
            return baseResponse.genSuccessResp();
        }
        Map<String,Object> map = new HashedMap();
        String key = "";
        List<Object> list = new ArrayList<>();
        GoodsInfoAddCount goodsInfoAddCount = null;
        for(GoodsInfo tempGoodsInfo:goodsInfos){
            if(StringUtils.isBlank(key)){
                key = tempGoodsInfo.getGoodsType();
            }
            if(!key.equals(tempGoodsInfo.getGoodsType())){
                map.put(key,list);
                list = new ArrayList<>();
                key = tempGoodsInfo.getGoodsType();
            }
            goodsInfoAddCount = new GoodsInfoAddCount();
            BeanUtils.copyProperties(tempGoodsInfo,goodsInfoAddCount);
            list.add(goodsInfoAddCount);
        }
        map.put(key,list);
        return baseResponse.genSuccessResp(map);
    }



    @Override
    public BaseResponse deleteGoods(Integer id){
        logger.info("deleteGoods id:[{}]", id);
        BaseResponse baseResponse = new BaseResponse();
        if(id==null){
            return baseResponse.genFail("param error");
        }
        goodsInfoMapper.deleteByPrimaryKey(id);
        return baseResponse.genSuccessResp();
    }


    @Override
    public BaseResponse saveOrUpdateGoods(GoodsInfo req){
        logger.info("saveGoods req:[{}]", JSONObject.toJSONString(req));
        BaseResponse baseResponse = new BaseResponse();
        if(req.getId()==null){
            SaveGoodsInfoReq saveGoodsInfoReq = new SaveGoodsInfoReq();
            BeanUtils.copyProperties(req,saveGoodsInfoReq);
           return saveGoods(saveGoodsInfoReq);
        }else{
            UpdateGoodsInfoReq updateGoodsInfoReq = new UpdateGoodsInfoReq();
            BeanUtils.copyProperties(req,updateGoodsInfoReq);
            return updateGoods(updateGoodsInfoReq);
        }

    }


    @Override
    public BaseResponse saveGoods(SaveGoodsInfoReq req){
        logger.info("saveGoods req:[{}]", JSONObject.toJSONString(req));
        BaseResponse baseResponse = new BaseResponse();

        Map<String ,String> map= CheckArgsUtil.checkArgsMap(req);
        if (map!=null) {
            return baseResponse.genFail(map.values().iterator().next());
        }

        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(req,goodsInfo);
        goodsInfoMapper.insertSelective(goodsInfo);
        return baseResponse.genSuccessResp();
    }

    @Override
    public BaseResponse updateGoods(UpdateGoodsInfoReq req){
        logger.info("updateGoods req:[{}]", JSONObject.toJSONString(req));
        BaseResponse baseResponse = new BaseResponse();
        if(req.getId()==null){
            return baseResponse.genFail("参数错误");
        }
        MyBeanUtil.convertEmptyToNull(req);

        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(req,goodsInfo);
        goodsInfoMapper.updateByPrimaryKeySelective(goodsInfo);
        return baseResponse.genSuccessResp();
    }




    @Override
    public BaseResponse queryGoodsInfo(QueryGoodsInfoReq req){
        logger.info("queryGoods req:[{}]", JSONObject.toJSONString(req));
        BaseResponse baseResponse = new BaseResponse();
        MyBeanUtil.convertEmptyToNull(req);

        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(req,goodsInfo);
        List<GoodsInfo> goodsInfos = goodsInfoMapper.selectPage(goodsInfo, null);
        return baseResponse.genSuccessResp(goodsInfos);
    }


    @Override
    public BaseResponse queryGoodsInfosLike(QueryGoodsInfosLikeReq req){
        logger.info("queryGoodsInfosLike req:[{}]", JSONObject.toJSONString(req));
        BaseResponse baseResponse = new BaseResponse();
        MyBeanUtil.convertEmptyToNull(req);

        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(req,goodsInfo);
        List<GoodsInfo> goodsInfos = goodsInfoMapper.queryGoodsInfosLike(goodsInfo, null);
        return baseResponse.genSuccessResp(goodsInfos);
    }



    @Override
    public BaseResponse updateOrder(Orders req){
        logger.info("updateOrder req:[{}]", JSONObject.toJSONString(req));
        BaseResponse baseResponse = new BaseResponse();
        MyBeanUtil.convertEmptyToNull(req);

        Orders orders = new Orders();
        BeanUtils.copyProperties(req,orders);
        int i = ordersMapper.updateByPrimaryKeySelective(orders);
        return baseResponse.genSuccessResp(i);
    }


    @Override
    public BaseResponse queryOrdersLike(Orders req){
        logger.info("queryOrdersLike req:[{}]", JSONObject.toJSONString(req));
        BaseResponse baseResponse = new BaseResponse();
        MyBeanUtil.convertEmptyToNull(req);
        List<Orders> orderss = ordersMapper.queryOrdersLike(req, null);
        return baseResponse.genSuccessResp(orderss);
    }
    @Override
    public BaseResponse queryOrders(Orders req){
        logger.info("queryOrders req:[{}]", JSONObject.toJSONString(req));
        BaseResponse baseResponse = new BaseResponse();
        MyBeanUtil.convertEmptyToNull(req);

        Orders orders = new Orders();
        BeanUtils.copyProperties(req,orders);
        List<Orders> orderss = ordersMapper.selectPage(orders, null);
        return baseResponse.genSuccessResp(orderss);
    }


    @Override
    public BaseResponse queryOrderAndInfosByOrderNo(String orderNo){
        logger.info("queryOrderAndInfosByOrderNo req:[{}]", orderNo);
        BaseResponse baseResponse = new BaseResponse();
        if(StringUtils.isBlank(orderNo)){
            return baseResponse.genFail("param error");
        }

        Orders orders = new Orders();
        orders.setOrderNo(orderNo);
        List<Orders> orderss = ordersMapper.selectPage(orders, PageRequest.of(0, 1));

        OrderGoodsInfo orderGoodsInfo = new OrderGoodsInfo();
        orderGoodsInfo.setOrderNo(orderNo);
        List<OrderGoodsInfo> orderGoodsInfos = orderGoodsInfoMapper.selectPage(orderGoodsInfo, null);

        Map<String,Object> map = new HashedMap();
        map.put("order",orderss.get(0));
        map.put("orderGoodsInfo",orderGoodsInfos);
        return baseResponse.genSuccessResp(map);
    }


    @Override
    public BaseResponse queryOrderInfos(QueryOrderInfosReq req){
        logger.info("queryOrderInfos req:[{}]", JSONObject.toJSONString(req));
        BaseResponse baseResponse = new BaseResponse();
        MyBeanUtil.convertEmptyToNull(req);

        OrderGoodsInfo orderGoodsInfo = new OrderGoodsInfo();
        BeanUtils.copyProperties(req,orderGoodsInfo);
        List<OrderGoodsInfo> orderss = orderGoodsInfoMapper.selectPage(orderGoodsInfo, null);
        return baseResponse.genSuccessResp(orderss);
    }








}
