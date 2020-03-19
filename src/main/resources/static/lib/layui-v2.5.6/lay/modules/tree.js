/** layui-v2.5.6 MIT License By https://www.layui.com */ ;
layui.define("form", function(e) {
    "use strict";
    let i = layui.$,
        form = layui.form,
        layer = layui.layer,
        tree = "tree",
        r = {
            config: {},
            index: layui[tree] ? layui[tree].index + 1e4 : 0,
            set: function(e) {
                let a = this;
                return a.config = i.extend({}, a.config, e), a
            },
            on: function(e, i) {
                return layui.onevent.call(this, tree, e, i)
            }
        },
        l = function() {
            let l1 = this,
                config = l1.config,
                id_index = config.id || l1.index;
            return l.that[id_index] = l1, l.config[id_index] = config, {
                config: config,
                reload: function(i) {
                    l1.reload.call(l1, i)
                },
                getChecked: function() {
                    return l1.getChecked.call(l1)
                },
                setChecked: function(i) {
                    return l1.setChecked.call(l1, i)
                }
            }
        },
        layuiHide = "layui-hide",
        layuiDisabled = "layui-disabled",
        layuiTreeSet = "layui-tree-set",
        layuiTreeIconClick = "layui-tree-iconClick",
        layuiIconAddition = "layui-icon-addition",
        layuiIconSubtraction = "layui-icon-subtraction",
        layuiTreeEntry = "layui-tree-entry",
        layuiTreeMain = "layui-tree-main",
        layuiTreeTxt = "layui-tree-txt",
        layuiTreePack = "layui-tree-pack",
        layuiTreeSpread = "layui-tree-spread",
        layuiTreeSetLineShort = "layui-tree-setLineShort",
        layuiTreeShowLine = "layui-tree-showLine",
        layuiTreeLineExtend = "layui-tree-lineExtend",
        Class = function(e) {
            let a = this;
            a.index = ++r.index,
                a.config = i.extend({},
                    a.config,
                    r.config, e),
                a.render()
        };
    //默认配置
    Class.prototype.config = {
        data: [], //数据
        showCheckbox: !1, //是否显示复选框
        showLine: !0, //是否开启连接线
        accordion: !1, //是否开启手风琴模式
        onlyIconControl: !1, //是否仅允许节点左侧图标控制展开收缩
        isJump: !1, //是否允许点击节点时弹出新窗口跳转
        edit: !1, //是否开启节点的操作图标
        //drag: !1, //是否开启节点拖拽
        customOperate: !1, //节点操作是否支持自定义
        limitNodeAddLevel: -1, //设置第X级节点不允许添加操作
        limitNodeDelLevel: -1, //设置第X级节点不允许删除操作
        //nodeIcon: "", //节点图标
        text: {
            defaultNodeName: "未命名", //节点默认名称
            none: "无数据" //数据为空的默认提示
        }
    },
        Class.prototype.reload = function(e) {
            let a = this;
            layui.each(e, function(e, i) {
                i.constructor === Array && delete a.config[e]
            }), a.config = i.extend(!0, {}, a.config, e), a.render()
        },
        Class.prototype.render = function() {
            let e = this,
                a = e.config;
            e.checkids = [];
            let n = i('<div class="layui-tree' + (a.showCheckbox ? " layui-form" : "") + (a.showLine ? " layui-tree-line" : "") +
                '" lay-filter="LAY-tree-' + e.index + '"></div>');
            e.tree(n);
            let t = a.elem = i(a.elem);
            if (t[0]) {
                if (e.key = a.id || e.index, e.elem = n, e.elemNone = i('<div class="layui-tree-emptyText">' + a.text.none +
                    "</div>"), t.html(e.elem), 0 == e.elem.find(".layui-tree-set").length) return e.elem.append(e.elemNone);
                a.showCheckbox && e.renderForm("checkbox"), e.elem.find(".layui-tree-set").each(function() {
                    let e = i(this);
                    e.parent(".layui-tree-pack")[0] || e.addClass("layui-tree-setHide"), !e.next()[0] && e.parents(
                        ".layui-tree-pack").eq(1).hasClass("layui-tree-lineExtend") && e.addClass(layuiTreeSetLineShort), e.next()[0] || e.parents(
                        ".layui-tree-set").eq(0).next()[0] || e.addClass(layuiTreeSetLineShort)
                }), e.events()
            }
        },
        Class.prototype.renderForm = function(e) {
            form.render(e, "LAY-tree-" + this.index)
        },
        Class.prototype.tree = function(e, a) {
            let n = this,
                t = n.config,
                r = a || t.data;
            layui.each(r, function(a, r) {
                let l = r.children && r.children.length > 0,
                    o = i('<div class="layui-tree-pack" ' + (r.spread ? 'style="display: block;"' : "") + '"></div>'),
                    h = i(['<div data-id="' + r.id + '" class="layui-tree-set' + (r.spread ? " layui-tree-spread" : "") + (r.checked ?
                        " layui-tree-checkedFirst" : "") + '">', '<div class="layui-tree-entry">', '<div class="layui-tree-main">',
                        function() {
                            return t.showLine ? l ? '<span class="layui-tree-iconClick layui-tree-icon"><i class="layui-icon ' + (r.spread ?
                                "layui-icon-subtraction" : "layui-icon-addition") + '"></i></span>' :
                                '<span class="layui-tree-iconClick"><i class="layui-icon layui-icon-file"></i></span>' :
                                '<span class="layui-tree-iconClick"><i class="layui-tree-iconArrow ' + (l ? "" : layuiHide) + '"></i></span>'
                        }(),
                        function() {
                            return t.showCheckbox ? '<input type="checkbox" name="' + (r.field || "layuiTreeCheck_" + r.id) +
                                '" same="layuiTreeCheck" lay-skin="primary" ' + (r.disabled ? "disabled" : "") + ' value="' + r.id + '">' :
                                ""
                        }(),
                        function() {
                            return t.isJump && r.href ? '<a href="' + r.href + '" target="_blank" class="' + layuiTreeTxt + '">' + (r.title || r.label ||
                                t.text.defaultNodeName) + "</a>" : '<span class="' + layuiTreeTxt + (r.disabled ? " " + layuiDisabled : "") + '">' + (r.title ||
                                r.label || t.text.defaultNodeName) + "</span>"
                        }(), "</div>",
                        //节点操作图标
                        function() {
                            if (!t.edit) return "";
                            let e = {
                                add: '<i class="layui-icon layui-icon-add-1"  data-type="add"></i>',
                                update: '<i class="layui-icon layui-icon-edit" data-type="update"></i>',
                                del: '<i class="layui-icon layui-icon-delete" data-type="del"></i>'
                            };
                            if (t.limitNodeAddLevel > -1 && r.type >= t.limitNodeAddLevel) {
                                e = {
                                    update: '<i class="layui-icon layui-icon-edit" data-type="update"></i>',
                                    del: '<i class="layui-icon layui-icon-delete" data-type="del"></i>'
                                }
                            }
                            if (t.limitNodeDelLevel > -1 && r.type === t.limitNodeDelLevel) {
                                e = {
                                    add: '<i class="layui-icon layui-icon-add-1"  data-type="add"></i>',
                                    update: '<i class="layui-icon layui-icon-edit" data-type="update"></i>'
                                }
                            }
                            let i = ['<div class="layui-btn-group layui-tree-btnGroup">'];
                            return t.edit === !0 && (t.edit = ["update", "del"]), "object" == typeof t.edit ? (layui.each(t.edit,
                                function(a, n) {
                                    i.push(e[n] || "")
                                }), i.join("") + "</div>") : void 0
                        }(), "</div></div>"
                    ].join(""));
                l && (h.append(o), n.tree(o, r.children)), e.append(h), h.prev("." + layuiTreeSet)[0] && h.prev().children(
                    ".layui-tree-pack").addClass("layui-tree-showLine"), l || h.parent(".layui-tree-pack").addClass(
                    "layui-tree-lineExtend"), n.spread(h, r), t.showCheckbox && (r.checked && n.checkids.push(r.id), n.checkClick(
                    h, r)), t.edit && n.operate(h, r)
            })
        },
        Class.prototype.spread = function(e, a) {
            let n = this,
                t = n.config,
                r = e.children("." + layuiTreeEntry),
                l = r.children("." + layuiTreeMain),
                c = r.find("." + layuiTreeIconClick),
                k = r.find("." + layuiTreeTxt),
                m = t.onlyIconControl ? c : l,
                x = "";
            m.on("click", function(i) {
                let a = e.children("." + layuiTreePack),
                    n = m.children(".layui-icon")[0] ? m.children(".layui-icon") : m.find(".layui-tree-icon").children(
                        ".layui-icon");
                if (a[0]) {
                    if (e.hasClass(layuiTreeSpread)) e.removeClass(layuiTreeSpread), a.slideUp(200), n.removeClass(layuiIconSubtraction).addClass(layuiIconAddition);
                    else if (e.addClass(layuiTreeSpread), a.slideDown(200), n.addClass(layuiIconSubtraction).removeClass(layuiIconAddition), t.accordion) {
                        let r = e.siblings("." + layuiTreeSet);
                        r.removeClass(layuiTreeSpread), r.children("." + layuiTreePack).slideUp(200), r.find(".layui-tree-icon").children(".layui-icon").removeClass(
                            layuiIconSubtraction).addClass(layuiIconAddition)
                    }
                } else x = "normal"
            }), k.on("click", function() {
                let n = i(this);
                n.hasClass(layuiDisabled) || (x = e.hasClass(layuiTreeSpread) ? t.onlyIconControl ? "open" : "close" : t.onlyIconControl ? "close" :
                    "open", t.click && t.click({
                    elem: e,
                    state: x,
                    data: a
                }))
            })
        },
        Class.prototype.setCheckbox = function(e, i, a) {
            let n = this,
                t = (n.config, a.prop("checked"));
            if (!a.prop("disabled")) {
                if ("object" == typeof i.children || e.find("." + layuiTreePack)[0]) {
                    let r = e.find("." + layuiTreePack).find('input[same="layuiTreeCheck"]');
                    r.each(function() {
                        this.disabled || (this.checked = t)
                    })
                }
                let l = function(e) {
                    if (e.parents("." + layuiTreeSet)[0]) {
                        let i, a = e.parent("." + layuiTreePack),
                            n = a.parent(),
                            r = a.prev().find('input[same="layuiTreeCheck"]');
                        t ? r.prop("checked", t) : (a.find('input[same="layuiTreeCheck"]').each(function() {
                            this.checked && (i = !0)
                        }), i || r.prop("checked", !1)), l(n)
                    }
                };
                l(e), n.renderForm("checkbox")
            }
        },
        Class.prototype.checkClick = function(e, a) {
            let n = this,
                t = n.config,
                r = e.children("." + layuiTreeEntry),
                l = r.children("." + layuiTreeMain);
            l.on("click", 'input[same="layuiTreeCheck"]+', function(r) {
                layui.stope(r);
                let l = i(this).prev(),
                    c = l.prop("checked");
                l.prop("disabled") || (n.setCheckbox(e, a, l), t.oncheck && t.oncheck({
                    elem: e,
                    checked: c,
                    data: a
                }))
            })
        },
        //节点操作
        Class.prototype.operate = function(e, a) {
            //console.log(e);
            let operate = this,
                config = operate.config,
                childrenLayuiTreeEntry = e.children("." + layuiTreeEntry),
                childrenLayuiTreeMain = childrenLayuiTreeEntry.children("." + layuiTreeMain);
            childrenLayuiTreeEntry.children(".layui-tree-btnGroup").on("click", ".layui-icon", function(l) {
                layui.stope(l);//阻止节点操作
                let dataType = i(this).data("type"),
                    b = e.children("." + layuiTreePack),
                    g = {
                        data: a,
                        type: dataType,
                        elem: e
                    };
                if (config.customOperate) {
                    //执行操作后回调
                    config.operate && config.operate(g)
                } else {
                    if ("add" === dataType) {
                        let w = config.operate && config.operate(g),
                            N = {};
                        if(w < 0 )
                            return;
                        //传递type和id
                        let w1 = w.split(' ');
                        //若节点本身无子节点
                        b[0] ||
                        (config.showLine ?
                            //开启连接线
                            (childrenLayuiTreeMain.find("." + layuiTreeIconClick).addClass("layui-tree-icon"),
                                childrenLayuiTreeMain.find("." + layuiTreeIconClick).children(".layui-icon").addClass(layuiIconAddition)
                                    .removeClass("layui-icon-file")) :
                            //若未开启连接线，显示箭头
                            childrenLayuiTreeMain.find(".layui-tree-iconArrow").removeClass(layuiHide), e.append(
                            '<div class="layui-tree-pack"></div>'));

                        if (N.title = config.text.defaultNodeName, N.type = w1[0], N.id = w1[1], N.classNo = w1[2], operate.tree(e.children("." + layuiTreePack), [N]), config.showLine)
                            if (b[0]) b.hasClass(layuiTreeLineExtend) || b.addClass(layuiTreeLineExtend), e.find("." + layuiTreePack).each(function() {
                                i(this).children("." + layuiTreeSet).last().addClass(layuiTreeSetLineShort)
                            }), b.children("." + layuiTreeSet).last().prev().hasClass(layuiTreeSetLineShort) ? b.children("." + layuiTreeSet).last().prev().removeClass(layuiTreeSetLineShort) : b.children(
                                "." + layuiTreeSet).last().removeClass(layuiTreeSetLineShort), !e.parent("." + layuiTreePack)[0] && e.next()[0] && b.children("." + layuiTreeSet).last().removeClass(
                                layuiTreeSetLineShort);
                            else {
                                let T = e.siblings("." + layuiTreeSet),
                                    L = 1,
                                    A = e.parent("." + layuiTreePack);
                                layui.each(T, function(e, a) {
                                    i(a).children("." + layuiTreePack)[0] || (L = 0)
                                }), 1 == L ? (T.children("." + layuiTreePack).addClass(layuiTreeShowLine), T.children("." + layuiTreePack).children("." + layuiTreeSet).removeClass(layuiTreeSetLineShort), e.children(
                                    "." + layuiTreePack).addClass(layuiTreeShowLine), A.removeClass(layuiTreeLineExtend), A.children("." + layuiTreeSet).last().children("." + layuiTreePack).children("." + layuiTreeSet).last()
                                    .addClass(layuiTreeSetLineShort)) : e.children("." + layuiTreePack).children("." + layuiTreeSet).addClass(layuiTreeSetLineShort)
                            } if (!config.showCheckbox) return;
                        if (childrenLayuiTreeMain.find('input[same="layuiTreeCheck"]')[0].checked) {
                            let I = e.children("." + layuiTreePack).children("." + layuiTreeSet).last();
                            I.find('input[same="layuiTreeCheck"]')[0].checked = !0
                        }
                        operate.renderForm("checkbox")
                    } else if ("update" === dataType) {
                        let F = childrenLayuiTreeMain.children("." + layuiTreeTxt).html();
                        childrenLayuiTreeMain.children("." + layuiTreeTxt).html(""), childrenLayuiTreeMain.append('<input type="text" class="layui-tree-editInput">'), childrenLayuiTreeMain.children(
                            ".layui-tree-editInput").val(F).focus();
                        let j = function(e) {
                            let newValue = e.val().trim();
                            newValue = newValue ? newValue : config.text.defaultNodeName, e.remove(), childrenLayuiTreeMain.children("." + layuiTreeTxt).html(newValue), g.data.title = newValue, config.operate &&
                            config.operate(g)
                        };
                        childrenLayuiTreeMain.children(".layui-tree-editInput").blur(function() {
                            j(i(this))
                        }), childrenLayuiTreeMain.children(".layui-tree-editInput").on("keydown", function(e) {
                            13 === e.keyCode && (e.preventDefault(), j(i(this)))
                        })
                    } else layer.confirm('确认删除该节点 "<span style="color: #999;">' + (a.title || "") + '</span>" 吗？', function(a) {
                        if (config.operate && config.operate(g), g.status = "remove", layer.close(a), !e.prev("." + layuiTreeSet)[0] && !e.next("." + layuiTreeSet)[0] &&
                        !e.parent("." + layuiTreePack)[0]) return e.remove(), void operate.elem.append(operate.elemNone);
                        if (e.siblings("." + layuiTreeSet).children("." + layuiTreeEntry)[0]) {
                            if (config.showCheckbox) {
                                let l = function(e) {
                                    if (e.parents("." + layuiTreeSet)[0]) {
                                        let a = e.siblings("." + layuiTreeSet).children("." + layuiTreeEntry),
                                            n = e.parent("." + layuiTreePack).prev(),
                                            r = n.find('input[same="layuiTreeCheck"]')[0],
                                            c = 1,
                                            d = 0;
                                        0 == r.checked && (a.each(function(e, a) {
                                            let n = i(a).find('input[same="layuiTreeCheck"]')[0];
                                            0 != n.checked || n.disabled || (c = 0), n.disabled || (d = 1)
                                        }), 1 == c && 1 == d && (r.checked = !0, operate.renderForm("checkbox"), l(n.parent("." + layuiTreeSet))))
                                    }
                                };
                                l(e)
                            }
                            if (config.showLine) {
                                let d = e.siblings("." + layuiTreeSet),
                                    h = 1,
                                    f = e.parent("." + layuiTreePack);
                                layui.each(d, function(e, a) {
                                    i(a).children("." + layuiTreePack)[0] || (h = 0)
                                }), 1 == h ? (b[0] || (f.removeClass(layuiTreeLineExtend), d.children("." + layuiTreePack).addClass(layuiTreeShowLine), d.children("." + layuiTreePack).children(
                                    "." + layuiTreeSet).removeClass(layuiTreeSetLineShort)), e.next()[0] ? f.children("." + layuiTreeSet).last().children("." + layuiTreePack).children("." + layuiTreeSet)
                                    .last()
                                    .addClass(layuiTreeSetLineShort) : e.prev().children("." + layuiTreePack).children("." + layuiTreeSet).last().addClass(layuiTreeSetLineShort), e.next()[0] || e.parents(
                                    "." + layuiTreeSet)[1] || e.parents("." + layuiTreeSet).eq(0).next()[0] || e.prev("." + layuiTreeSet).addClass(layuiTreeSetLineShort)) : !e.next()[0] && e.hasClass(
                                    layuiTreeSetLineShort) && e.prev().addClass(layuiTreeSetLineShort)
                            }
                        } else {
                            let y = e.parent("." + layuiTreePack).prev();
                            if (config.showLine) {
                                y.find("." + layuiTreeIconClick).removeClass("layui-tree-icon"), y.find("." + layuiTreeIconClick).children(".layui-icon").removeClass(layuiIconSubtraction).addClass(
                                    "layui-icon-file");
                                let w = y.parents("." + layuiTreePack).eq(0);
                                w.addClass(layuiTreeLineExtend), w.children("." + layuiTreeSet).each(function() {
                                    i(this).children("." + layuiTreePack).children("." + layuiTreeSet).last().addClass(layuiTreeSetLineShort)
                                })
                            } else y.find(".layui-tree-iconArrow").addClass(layuiHide);
                            e.parents("." + layuiTreeSet).eq(0).removeClass(layuiTreeSpread), e.parent("." + layuiTreePack).remove()
                        }
                        e.remove()
                    })
                }
            })
        },
        Class.prototype.events = function() {
            let e = this,
                a = e.config;
            e.elem.find(".layui-tree-checkedFirst");
            e.setChecked(e.checkids), e.elem.find(".layui-tree-search").on("keyup", function() {
                let n = i(this),
                    t = n.val(),
                    r = n.nextAll(),
                    l = [];
                r.find("." + layuiTreeTxt).each(function() {
                    let e = i(this).parents("." + layuiTreeEntry);
                    if (i(this).html().indexOf(t) != -1) {
                        l.push(i(this).parent());
                        let a = function(e) {
                            e.addClass("layui-tree-searchShow"), e.parent("." + layuiTreePack)[0] && a(e.parent("." + layuiTreePack).parent("." + layuiTreeSet))
                        };
                        a(e.parent("." + layuiTreeSet))
                    }
                }), r.find("." + layuiTreeEntry).each(function() {
                    let e = i(this).parent("." + layuiTreeSet);
                    e.hasClass("layui-tree-searchShow") || e.addClass(layuiHide)
                }), 0 == r.find(".layui-tree-searchShow").length && e.elem.append(e.elemNone), a.onsearch && a.onsearch({
                    elem: l
                })
            }), e.elem.find(".layui-tree-search").on("keydown", function() {
                i(this).nextAll().find("." + layuiTreeEntry).each(function() {
                    let e = i(this).parent("." + layuiTreeSet);
                    e.removeClass("layui-tree-searchShow " + layuiHide)
                }), i(".layui-tree-emptyText")[0] && i(".layui-tree-emptyText").remove()
            })
        },
        Class.prototype.getChecked = function() {
            let e = this,
                a = e.config,
                n = [],
                t = [];
            e.elem.find(".layui-form-checked").each(function() {
                n.push(i(this).prev()[0].value)
            });
            let r = function(e, a) {
                layui.each(e, function(e, t) {
                    layui.each(n, function(e, n) {
                        if (t.id == n) {
                            let l = i.extend({}, t);
                            return delete l.children, a.push(l), t.children && (l.children = [], r(t.children, l.children)), !0
                        }
                    })
                })
            };
            return r(i.extend({}, a.data), t), t
        },
        Class.prototype.setChecked = function(e) {
            let a = this;
            a.config;
            a.elem.find("." + layuiTreeSet).each(function(a, n) {
                let t = i(this).data("id"),
                    r = i(n).children("." + layuiTreeEntry).find('input[same="layuiTreeCheck"]'),
                    l = r.next();
                if ("number" == typeof e) {
                    if (t == e) return r[0].checked || l.click(), !1
                } else "object" == typeof e && layui.each(e, function(e, i) {
                    if (i == t && !r[0].checked) return l.click(), !0
                })
            })
        }, l.that = {}, l.config = {}, r.reload = function(e, i) {
        let a = l.that[e];
        return a.reload(i), l.call(a)
    }, r.getChecked = function(e) {
        let i = l.that[e];
        return i.getChecked()
    }, r.setChecked = function(e, i) {
        let a = l.that[e];
        return a.setChecked(i)
    }, r.render = function(e) {
        let i = new Class(e);
        return l.call(i)
    }, e(tree, r)
});
