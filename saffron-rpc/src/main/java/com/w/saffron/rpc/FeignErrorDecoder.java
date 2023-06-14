package com.w.saffron.rpc;

import com.w.saffron.common.R;
import com.w.saffron.exception.OprException;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author w
 * @since 2023/6/13
 */
@Component
public class FeignErrorDecoder implements Decoder, SmartInitializingSingleton {
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    private ResponseEntityDecoder responseEntityDecoder;
    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        if (response.status()==200){
            Object decode = responseEntityDecoder.decode(response, type);
            R<?> r = (R<?>) decode;
            if (r.getCode()!=200){
                if (r.getCode()==405){
                    throw new OprException(r.getData().toString());
                }
                throw new OprException(r.getMsg());
            }
            return r;
        }
        throw new OprException("请求异常");
    }

    @Override
    public void afterSingletonsInstantiated() {
        responseEntityDecoder = new ResponseEntityDecoder(new SpringDecoder(this.messageConverters));
    }
}
