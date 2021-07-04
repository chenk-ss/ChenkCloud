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