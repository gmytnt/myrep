package com.mytnt.tool;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;


public class SendSms {
    public static String sendSms(String phone,String code,String time){
            try {
             /*必要步骤：
             * 实例化一个认证对象，入参需要传入腾讯云账户密钥对secretId，secretKey。
             * 这里采用的是从环境变量读取的方式，需要在环境变量中先设置这两个值。
             * 你也可以直接在代码中写死密钥对，但是小心不要将代码复制、上传或者分享给他人，
             * 以免泄露密钥对危及你的财产安全。
             * CAM密匙查询: https://console.cloud.tencent.com/cam/capi
             * */

                Credential cred = new Credential("", "");
                HttpProfile httpProfile = new HttpProfile();
                httpProfile.setEndpoint("sms.tencentcloudapi.com");
                ClientProfile clientProfile = new ClientProfile();
                clientProfile.setHttpProfile(httpProfile);
                SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);

             /*实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
             * 你可以直接查询SDK源码确定接口有哪些属性可以设置
             * 属性可能是基本类型，也可能引用了另一个数据结构
             * 推荐使用IDE进行开发，可以方便的跳转查阅各个接口和数据结构的文档说明 */
                SendSmsRequest req = new SendSmsRequest();

             /*填充请求参数,这里request对象的成员变量即对应接口的入参
             * 你可以通过官网接口文档或跳转到request对象的定义处查看请求参数的定义
             * 基本类型的设置:
             * 帮助链接：
             * 短信控制台: https://console.cloud.tencent.com/sms/smslist
             * sms helper: https://cloud.tencent.com/document/product/382/3773

             短信应用ID: 短信SdkAppid在 [短信控制台] 添加应用后生成的实际SdkAppid，示例如1400006666 */
                String appid = "1400331237";
                req.setSmsSdkAppid(appid);

             /*短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，签名信息可登录 [短信控制台] 查看 */
                String sign = "篮球那些事";
                req.setSign(sign);

//             模板 ID: 必须填写已审核通过的模板 ID。模板ID可登录 [短信控制台] 查看
                String templateID = "551854";
                req.setTemplateID(templateID);

      /*       下发手机号码，采用 e.164 标准，+[国家或地区码][手机号]
             * 示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号*/
                String[] phoneNumbers = {"+86"+phone};
                req.setPhoneNumberSet(phoneNumbers);

//             模板参数: 若无模板参数，则设置为空
                String[] templateParams = {code, time};
                req.setTemplateParamSet(templateParams);

            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */

             SendSmsResponse res = client.SendSms(req);

                // 输出json格式的字符串回包
                System.out.println(SendSmsResponse.toJsonString(res));

                // 也可以取出单个值，你可以通过官网接口文档或跳转到response对象的定义处查看返回字段的定义
                System.out.println(res.getRequestId());
                System.out.println(res.getSendStatusSet()[0].getCode());
                return res.getSendStatusSet()[0].getCode();
            } catch (TencentCloudSDKException e) {
                e.printStackTrace();
                return "";
            }

    }
}
