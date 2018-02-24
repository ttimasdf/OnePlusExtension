# OnePlusExtension

为氧OS增加氢OS的一些功能，如短信识别、通话录音等等

## 已知功能

- 通话录音
- 短信识别（包含验证码／垃圾短信／通知短信识别）
- 陌生号码识别
- 流量校正

请在最下面的链接中下载 liuliangbao.apk 与 YuloreFramework.apk （皆从氢OS中提取），否则流量校正与来电识别将不能正常工作。

YuloreFramework.apk 原版因应用签名问题无法正确安装，因此使用 [uber-apk-signer](//github.com/patrickfav/uber-apk-signer) 进行了重签。提取的原版 apk 位于 [auxiliary](/auxiliary) 目录中，感谢 [@zhongfly](//github.com/zhongfly) 提供的最新版。

# English version

Add H2OS(HydrogenOS) features to OxygenOS for OnePlus series, such as SMS recognition (for Chinese users), and call recording.

No UI for the time being including modification for SMS and Dialer app. 

Feature requests are welcomed!

## Known working functions
- Call recording
- SMS recognition/smart block
- Phone number recognition
- Network usage calibration


More packages should be installed to make everything work as expected.
- com.oupeng.max.sdk (aka. 流量宝) ： liuliangbao.apk
- com.yulore.framework (aka. 电话邦) ： YuloreFramework.apk

Downloads for apks above can be found below or manually extracted from H2OS

- [YuloreFramework-aligned-signed.apk](auxiliary/YuloreFramework-aligned-signed.apk?raw=true)
- [liuliangbao.apk](auxiliary/liuliangbao.apk?raw=true)
