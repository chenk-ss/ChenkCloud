var home = new Vue({
    el: '#app',
    data: {
        page: '',
        dataRead: '',
        data: [],
        size: 'hello',
    },
    methods: {
        start: function () {
            var _self = this;
            _self.queryList(0);
        },

        // 上一页
        lastList: function () {
            var _self = this;
            var pageNum = _self.page - 1;
            _self.queryList(pageNum);
        },

        // 下一页
        nextList: function () {
            var _self = this;
            var pageNum = _self.page + 1;
            _self.queryList(pageNum);
        },

        queryList: function (page) {
            var _self = this;
            getAjax(HOST_LIST_QUERY + page, null, function (ret, err) {
                if (ret) {
                    _self.page = ret.page;
                    _self.size = ret.size;
                    _self.data = ret.data;
                }
            });
        },

        copyUrl: function(url) {
            var input = document.createElement("input");   // js创建一个input输入框
            input.value = url;  // 将需要复制的文本赋值到创建的input输入框中
            document.body.appendChild(input);    // 将输入框暂时创建到实例里面
            input.select();   // 选中输入框中的内容
            document.execCommand("Copy");   // 执行复制操作
            document.body.removeChild(input); // 最后删除实例中临时创建的input输入框，完成复制操作
        },

        upload: function () {
            window.location.href = HOST_WEB_HTML;
        },

        confirmRemove: function (name) {
            var _self = this;
            var msg = "您真的确定要删除吗？\n\n请确认！";
            if (confirm(msg) == true) {
                _self.remove(name);
            }
        },

        remove: function (name) {
            var _self = this;
            cos.deleteObject({
                Bucket: BUCKET_NAME, /* 必须 */
                Region: REGION,     /* 存储桶所在地域，必须字段 */
                Key: name        /* 必须 */
            }, function (err, data) {
                console.log(err || data);
                if (data) {
                    getAjax(HOST_LIST_REMOVE + name, null, function (ret, err) {
                        if (ret) {
                            _self.queryList(_self.page);
                        }
                    });
                }
            });
        }
    },
    created() {
        this.start();
    }
})