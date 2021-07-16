var HOST = "http://localhost:7000";
var HOST_LIST_HTML = HOST + "/list.html";
var HOST_WEB_HTML = HOST + '/web.html';

var HOST_LIST_QUERY = HOST + '/file/list?size=20&page=';
var HOST_LIST_REMOVE = HOST + '/file/remove?fileName=';
var HOST_COS_TOKEN = HOST + '/file/cos/token';
var HOST_DB_INSERT = HOST + "/file/insertToDB"

var BUCKET_NAME = "taeyeon";
var REGION = "ap-nanjing";

function postAjax(url, data, callback) {
    $.ajax({
        url: url,
        data: data,
        type: "POST",
        contentType: "application/json",
        success: function (ret) {
            if (callback) {
                callback(ret);
            }
        },
        error: function (err) {
            if (callback) {
                callback(err);
            }
        }
    });
}

function getAjax(url, data, callback) {
    $.ajax({
        url: url,
        data: data,
        type: "GET",
        sync: false,
        contentType: "application/json",
        success: function (ret) {
            if (callback) {
                callback(ret);
            }
        },
        error: function (err) {
            if (callback) {
                callback(err);
            }
        }
    });
}

var cos = new COS({
    getAuthorization: function (options, callback) {
        var url = HOST_COS_TOKEN; // 这里替换成您的服务接口地址
        var xhr = new XMLHttpRequest();
        xhr.open('POST', url, true);
        xhr.onload = function (e) {
            try {
                var data = JSON.parse(e.target.responseText);
                var credentials = data.credentials;
            } catch (e) {
            }
            if (!data || !credentials) return console.error('credentials invalid');
            callback({
                TmpSecretId: credentials.tmpSecretId,
                TmpSecretKey: credentials.tmpSecretKey,
                XCosSecurityToken: credentials.sessionToken,
                StartTime: data.startTime, // 时间戳，单位秒，如：1580000000，建议返回服务器时间作为签名的开始时间，避免用户浏览器本地时间偏差过大导致签名错误
                ExpiredTime: data.expiredTime, // 时间戳，单位秒，如：1580000900
            });
        };
        xhr.send();
    }
});