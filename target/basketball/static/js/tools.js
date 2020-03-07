/*创建编辑器*/
function editorModify(editor) {
    // 通过 url 参数配置 debug 模式。url 中带有 wangeditor_debug_mode=1 才会开启 debug 模式
    editor.customConfig.debug = location.href.indexOf('wangeditor_debug_mode=1') > 0
    editor.customConfig.uploadImgServer = '/uploadFile';  // 上传图片到服务器
    //         editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
    // 隐藏“网络图片”tab
    editor.customConfig.showLinkImg = false;
    editor.customConfig.height = 1000
    editor.customConfig.uploadFileName = 'file';
    // 忽略粘贴内容中的图片
    editor.customConfig.pasteIgnoreImg = true;
    editor.customConfig.menus = [
        'head',  // 标题
        'bold',  // 粗体
        'fontSize',  // 字号
        'fontName',  // 字体
        'italic',  // 斜体
        'underline',  // 下划线
        'strikeThrough',  // 删除线
        'foreColor',  // 文字颜色
        'backColor',  // 背景颜色
        'link',  // 插入链接
        'justify',  // 对齐方式
        'emoticon',  // 表情
        'image',  // 插入图片
        'table',  // 表格
        'undo',  // 撤销
        'redo'  // 重复
    ]
    editor.create();
}
/*日期转换器*/
function dateFormat(date, format = "YYYY-MM-DD HH:mm:ss") {
    const config = {
        YYYY: date.getFullYear(),
        MM: date.getMonth() + 1,
        DD: date.getDate(),
        HH: date.getHours(),
        mm: date.getMinutes(),
        ss: date.getSeconds()
    };
    for (const key in config) {
        format = format.replace(key, config[key]);
    }
    return format;
}