var home = new Vue({
    el: '#app',
    data: {
        page: getPage(),
        dataRead: '',
        data: [],
        size: '',
        total: '',
        pwd: '',
        isLogin: getToken() != null && getToken() != '',
    },
    methods: {
        start: function () {
            var _self = this;
            _self.queryList(_self.page);
        },

        // 上一页
        lastList: function () {
            var _self = this;
            if (_self.page <= 1) {
                return;
            }
            var pageNum = _self.page - 1;
            _self.queryList(pageNum);
            sessionStorage.setItem("page", pageNum);
        },

        // 下一页
        nextList: function () {
            var _self = this;
            if (_self.size < 20 || _self.total <= _self.page * _self.size) {
                return;
            }
            var pageNum = _self.page + 1;
            _self.queryList(pageNum);
            sessionStorage.setItem("page", pageNum);
        },

        queryList: function (page) {
            var _self = this;
            getAjax(HOST_LIST_QUERY + page, null, function (ret, err) {
                if (ret) {
                    _self.page = ret.page;
                    _self.size = ret.size;
                    _self.data = ret.data;
                    _self.total = ret.total;
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

        open: function (url) {
            window.open(url);
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
                if (err) {
                    alert("您无删除权限");
                    return;
                }
                if (data) {
                    getAjax(HOST_LIST_REMOVE + name, null, function (ret, err) {
                        if (ret) {
                            _self.queryList(_self.page);
                        }
                    });
                }
            });
        },

        login: function () {
            var _self = this;
            console.log(_self.pwd);
            getAjax(HOST_LOGIN + _self.pwd, null, function (ret, err) {
                if (ret && ret != null) {
                    sessionStorage.setItem('token', ret);
                    _self.queryList(1);
                    _self.isLogin = true;
                }
            });
        },

        logout: function () {
            var _self = this;
            sessionStorage.setItem('token', '');
            _self.queryList(1);
            _self.pwd = '';
            _self.isLogin = false;
        }
    },
    created() {
        this.start();
    }
})