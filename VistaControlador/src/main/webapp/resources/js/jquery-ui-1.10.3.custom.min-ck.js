/*! jQuery UI - v1.10.3 - 2013-09-30
* http://jqueryui.com
* Includes: jquery.ui.core.js, jquery.ui.widget.js, jquery.ui.mouse.js, jquery.ui.position.js, jquery.ui.draggable.js, jquery.ui.droppable.js, jquery.ui.resizable.js, jquery.ui.selectable.js, jquery.ui.sortable.js, jquery.ui.accordion.js, jquery.ui.autocomplete.js, jquery.ui.button.js, jquery.ui.datepicker.js, jquery.ui.dialog.js, jquery.ui.menu.js, jquery.ui.progressbar.js, jquery.ui.slider.js, jquery.ui.spinner.js, jquery.ui.tabs.js, jquery.ui.tooltip.js, jquery.ui.effect.js, jquery.ui.effect-blind.js, jquery.ui.effect-bounce.js, jquery.ui.effect-clip.js, jquery.ui.effect-drop.js, jquery.ui.effect-explode.js, jquery.ui.effect-fade.js, jquery.ui.effect-fold.js, jquery.ui.effect-highlight.js, jquery.ui.effect-pulsate.js, jquery.ui.effect-scale.js, jquery.ui.effect-shake.js, jquery.ui.effect-slide.js, jquery.ui.effect-transfer.js
* Copyright 2013 jQuery Foundation and other contributors; Licensed MIT */(function(e, t) {
    function n(t, n) {
        var i, s, o, u = t.nodeName.toLowerCase();
        return "area" === u ? (i = t.parentNode, s = i.name, t.href && s && "map" === i.nodeName.toLowerCase() ? (o = e("img[usemap=#" + s + "]")[0], !!o && r(o)) : !1) : (/input|select|textarea|button|object/.test(u) ? !t.disabled : "a" === u ? t.href || n : n) && r(t);
    }
    function r(t) {
        return e.expr.filters.visible(t) && !e(t).parents().addBack().filter(function() {
            return "hidden" === e.css(this, "visibility");
        }).length;
    }
    var i = 0, s = /^ui-id-\d+$/;
    e.ui = e.ui || {}, e.extend(e.ui, {
        version: "1.10.3",
        keyCode: {
            BACKSPACE: 8,
            COMMA: 188,
            DELETE: 46,
            DOWN: 40,
            END: 35,
            ENTER: 13,
            ESCAPE: 27,
            HOME: 36,
            LEFT: 37,
            NUMPAD_ADD: 107,
            NUMPAD_DECIMAL: 110,
            NUMPAD_DIVIDE: 111,
            NUMPAD_ENTER: 108,
            NUMPAD_MULTIPLY: 106,
            NUMPAD_SUBTRACT: 109,
            PAGE_DOWN: 34,
            PAGE_UP: 33,
            PERIOD: 190,
            RIGHT: 39,
            SPACE: 32,
            TAB: 9,
            UP: 38
        }
    }), e.fn.extend({
        focus: function(t) {
            return function(n, r) {
                return "number" == typeof n ? this.each(function() {
                    var t = this;
                    setTimeout(function() {
                        e(t).focus(), r && r.call(t);
                    }, n);
                }) : t.apply(this, arguments);
            };
        }(e.fn.focus),
        scrollParent: function() {
            var t;
            return t = e.ui.ie && /(static|relative)/.test(this.css("position")) || /absolute/.test(this.css("position")) ? this.parents().filter(function() {
                return /(relative|absolute|fixed)/.test(e.css(this, "position")) && /(auto|scroll)/.test(e.css(this, "overflow") + e.css(this, "overflow-y") + e.css(this, "overflow-x"));
            }).eq(0) : this.parents().filter(function() {
                return /(auto|scroll)/.test(e.css(this, "overflow") + e.css(this, "overflow-y") + e.css(this, "overflow-x"));
            }).eq(0), /fixed/.test(this.css("position")) || !t.length ? e(document) : t;
        },
        zIndex: function(n) {
            if (n !== t) return this.css("zIndex", n);
            if (this.length) for (var r, i, s = e(this[0]); s.length && s[0] !== document; ) {
                if (r = s.css("position"), ("absolute" === r || "relative" === r || "fixed" === r) && (i = parseInt(s.css("zIndex"), 10), !isNaN(i) && 0 !== i)) return i;
                s = s.parent();
            }
            return 0;
        },
        uniqueId: function() {
            return this.each(function() {
                this.id || (this.id = "ui-id-" + ++i);
            });
        },
        removeUniqueId: function() {
            return this.each(function() {
                s.test(this.id) && e(this).removeAttr("id");
            });
        }
    }), e.extend(e.expr[":"], {
        data: e.expr.createPseudo ? e.expr.createPseudo(function(t) {
            return function(n) {
                return !!e.data(n, t);
            };
        }) : function(t, n, r) {
            return !!e.data(t, r[3]);
        },
        focusable: function(t) {
            return n(t, !isNaN(e.attr(t, "tabindex")));
        },
        tabbable: function(t) {
            var r = e.attr(t, "tabindex"), i = isNaN(r);
            return (i || r >= 0) && n(t, !i);
        }
    }), e("<a>").outerWidth(1).jquery || e.each([ "Width", "Height" ], function(n, r) {
        function i(t, n, r, i) {
            return e.each(s, function() {
                n -= parseFloat(e.css(t, "padding" + this)) || 0, r && (n -= parseFloat(e.css(t, "border" + this + "Width")) || 0), i && (n -= parseFloat(e.css(t, "margin" + this)) || 0);
            }), n;
        }
        var s = "Width" === r ? [ "Left", "Right" ] : [ "Top", "Bottom" ], o = r.toLowerCase(), u = {
            innerWidth: e.fn.innerWidth,
            innerHeight: e.fn.innerHeight,
            outerWidth: e.fn.outerWidth,
            outerHeight: e.fn.outerHeight
        };
        e.fn["inner" + r] = function(n) {
            return n === t ? u["inner" + r].call(this) : this.each(function() {
                e(this).css(o, i(this, n) + "px");
            });
        }, e.fn["outer" + r] = function(t, n) {
            return "number" != typeof t ? u["outer" + r].call(this, t) : this.each(function() {
                e(this).css(o, i(this, t, !0, n) + "px");
            });
        };
    }), e.fn.addBack || (e.fn.addBack = function(e) {
        return this.add(null == e ? this.prevObject : this.prevObject.filter(e));
    }), e("<a>").data("a-b", "a").removeData("a-b").data("a-b") && (e.fn.removeData = function(t) {
        return function(n) {
            return arguments.length ? t.call(this, e.camelCase(n)) : t.call(this);
        };
    }(e.fn.removeData)), e.ui.ie = !!/msie [\w.]+/.exec(navigator.userAgent.toLowerCase()), e.support.selectstart = "onselectstart" in document.createElement("div"), e.fn.extend({
        disableSelection: function() {
            return this.bind((e.support.selectstart ? "selectstart" : "mousedown") + ".ui-disableSelection", function(e) {
                e.preventDefault();
            });
        },
        enableSelection: function() {
            return this.unbind(".ui-disableSelection");
        }
    }), e.extend(e.ui, {
        plugin: {
            add: function(t, n, r) {
                var i, s = e.ui[t].prototype;
                for (i in r) s.plugins[i] = s.plugins[i] || [], s.plugins[i].push([ n, r[i] ]);
            },
            call: function(e, t, n) {
                var r, i = e.plugins[t];
                if (i && e.element[0].parentNode && 11 !== e.element[0].parentNode.nodeType) for (r = 0; i.length > r; r++) e.options[i[r][0]] && i[r][1].apply(e.element, n);
            }
        },
        hasScroll: function(t, n) {
            if ("hidden" === e(t).css("overflow")) return !1;
            var r = n && "left" === n ? "scrollLeft" : "scrollTop", i = !1;
            return t[r] > 0 ? !0 : (t[r] = 1, i = t[r] > 0, t[r] = 0, i);
        }
    });
})(jQuery);

(function(e, t) {
    var n = 0, r = Array.prototype.slice, i = e.cleanData;
    e.cleanData = function(t) {
        for (var n, r = 0; null != (n = t[r]); r++) try {
            e(n).triggerHandler("remove");
        } catch (s) {}
        i(t);
    }, e.widget = function(n, r, i) {
        var s, o, u, a, f = {}, l = n.split(".")[0];
        n = n.split(".")[1], s = l + "-" + n, i || (i = r, r = e.Widget), e.expr[":"][s.toLowerCase()] = function(t) {
            return !!e.data(t, s);
        }, e[l] = e[l] || {}, o = e[l][n], u = e[l][n] = function(e, n) {
            return this._createWidget ? (arguments.length && this._createWidget(e, n), t) : new u(e, n);
        }, e.extend(u, o, {
            version: i.version,
            _proto: e.extend({}, i),
            _childConstructors: []
        }), a = new r, a.options = e.widget.extend({}, a.options), e.each(i, function(n, i) {
            return e.isFunction(i) ? (f[n] = function() {
                var e = function() {
                    return r.prototype[n].apply(this, arguments);
                }, t = function(e) {
                    return r.prototype[n].apply(this, e);
                };
                return function() {
                    var n, r = this._super, s = this._superApply;
                    return this._super = e, this._superApply = t, n = i.apply(this, arguments), this._super = r, this._superApply = s, n;
                };
            }(), t) : (f[n] = i, t);
        }), u.prototype = e.widget.extend(a, {
            widgetEventPrefix: o ? a.widgetEventPrefix : n
        }, f, {
            constructor: u,
            namespace: l,
            widgetName: n,
            widgetFullName: s
        }), o ? (e.each(o._childConstructors, function(t, n) {
            var r = n.prototype;
            e.widget(r.namespace + "." + r.widgetName, u, n._proto);
        }), delete o._childConstructors) : r._childConstructors.push(u), e.widget.bridge(n, u);
    }, e.widget.extend = function(n) {
        for (var i, s, o = r.call(arguments, 1), u = 0, a = o.length; a > u; u++) for (i in o[u]) s = o[u][i], o[u].hasOwnProperty(i) && s !== t && (n[i] = e.isPlainObject(s) ? e.isPlainObject(n[i]) ? e.widget.extend({}, n[i], s) : e.widget.extend({}, s) : s);
        return n;
    }, e.widget.bridge = function(n, i) {
        var s = i.prototype.widgetFullName || n;
        e.fn[n] = function(u) {
            var a = "string" == typeof u, f = r.call(arguments, 1), l = this;
            return u = !a && f.length ? e.widget.extend.apply(null, [ u ].concat(f)) : u, a ? this.each(function() {
                var r, i = e.data(this, s);
                return i ? e.isFunction(i[u]) && "_" !== u.charAt(0) ? (r = i[u].apply(i, f), r !== i && r !== t ? (l = r && r.jquery ? l.pushStack(r.get()) : r, !1) : t) : e.error("no such method '" + u + "' for " + n + " widget instance") : e.error("cannot call methods on " + n + " prior to initialization; " + "attempted to call method '" + u + "'");
            }) : this.each(function() {
                var t = e.data(this, s);
                t ? t.option(u || {})._init() : e.data(this, s, new i(u, this));
            }), l;
        };
    }, e.Widget = function() {}, e.Widget._childConstructors = [], e.Widget.prototype = {
        widgetName: "widget",
        widgetEventPrefix: "",
        defaultElement: "<div>",
        options: {
            disabled: !1,
            create: null
        },
        _createWidget: function(t, r) {
            r = e(r || this.defaultElement || this)[0], this.element = e(r), this.uuid = n++, this.eventNamespace = "." + this.widgetName + this.uuid, this.options = e.widget.extend({}, this.options, this._getCreateOptions(), t), this.bindings = e(), this.hoverable = e(), this.focusable = e(), r !== this && (e.data(r, this.widgetFullName, this), this._on(!0, this.element, {
                remove: function(e) {
                    e.target === r && this.destroy();
                }
            }), this.document = e(r.style ? r.ownerDocument : r.document || r), this.window = e(this.document[0].defaultView || this.document[0].parentWindow)), this._create(), this._trigger("create", null, this._getCreateEventData()), this._init();
        },
        _getCreateOptions: e.noop,
        _getCreateEventData: e.noop,
        _create: e.noop,
        _init: e.noop,
        destroy: function() {
            this._destroy(), this.element.unbind(this.eventNamespace).removeData(this.widgetName).removeData(this.widgetFullName).removeData(e.camelCase(this.widgetFullName)), this.widget().unbind(this.eventNamespace).removeAttr("aria-disabled").removeClass(this.widgetFullName + "-disabled " + "ui-state-disabled"), this.bindings.unbind(this.eventNamespace), this.hoverable.removeClass("ui-state-hover"), this.focusable.removeClass("ui-state-focus");
        },
        _destroy: e.noop,
        widget: function() {
            return this.element;
        },
        option: function(n, r) {
            var i, s, o, u = n;
            if (0 === arguments.length) return e.widget.extend({}, this.options);
            if ("string" == typeof n) if (u = {}, i = n.split("."), n = i.shift(), i.length) {
                for (s = u[n] = e.widget.extend({}, this.options[n]), o = 0; i.length - 1 > o; o++) s[i[o]] = s[i[o]] || {}, s = s[i[o]];
                if (n = i.pop(), r === t) return s[n] === t ? null : s[n];
                s[n] = r;
            } else {
                if (r === t) return this.options[n] === t ? null : this.options[n];
                u[n] = r;
            }
            return this._setOptions(u), this;
        },
        _setOptions: function(e) {
            var t;
            for (t in e) this._setOption(t, e[t]);
            return this;
        },
        _setOption: function(e, t) {
            return this.options[e] = t, "disabled" === e && (this.widget().toggleClass(this.widgetFullName + "-disabled ui-state-disabled", !!t).attr("aria-disabled", t), this.hoverable.removeClass("ui-state-hover"), this.focusable.removeClass("ui-state-focus")), this;
        },
        enable: function() {
            return this._setOption("disabled", !1);
        },
        disable: function() {
            return this._setOption("disabled", !0);
        },
        _on: function(n, r, i) {
            var s, o = this;
            "boolean" != typeof n && (i = r, r = n, n = !1), i ? (r = s = e(r), this.bindings = this.bindings.add(r)) : (i = r, r = this.element, s = this.widget()), e.each(i, function(i, u) {
                function a() {
                    return n || o.options.disabled !== !0 && !e(this).hasClass("ui-state-disabled") ? ("string" == typeof u ? o[u] : u).apply(o, arguments) : t;
                }
                "string" != typeof u && (a.guid = u.guid = u.guid || a.guid || e.guid++);
                var f = i.match(/^(\w+)\s*(.*)$/), l = f[1] + o.eventNamespace, c = f[2];
                c ? s.delegate(c, l, a) : r.bind(l, a);
            });
        },
        _off: function(e, t) {
            t = (t || "").split(" ").join(this.eventNamespace + " ") + this.eventNamespace, e.unbind(t).undelegate(t);
        },
        _delay: function(e, t) {
            function n() {
                return ("string" == typeof e ? r[e] : e).apply(r, arguments);
            }
            var r = this;
            return setTimeout(n, t || 0);
        },
        _hoverable: function(t) {
            this.hoverable = this.hoverable.add(t), this._on(t, {
                mouseenter: function(t) {
                    e(t.currentTarget).addClass("ui-state-hover");
                },
                mouseleave: function(t) {
                    e(t.currentTarget).removeClass("ui-state-hover");
                }
            });
        },
        _focusable: function(t) {
            this.focusable = this.focusable.add(t), this._on(t, {
                focusin: function(t) {
                    e(t.currentTarget).addClass("ui-state-focus");
                },
                focusout: function(t) {
                    e(t.currentTarget).removeClass("ui-state-focus");
                }
            });
        },
        _trigger: function(t, n, r) {
            var i, s, o = this.options[t];
            if (r = r || {}, n = e.Event(n), n.type = (t === this.widgetEventPrefix ? t : this.widgetEventPrefix + t).toLowerCase(), n.target = this.element[0], s = n.originalEvent) for (i in s) i in n || (n[i] = s[i]);
            return this.element.trigger(n, r), !(e.isFunction(o) && o.apply(this.element[0], [ n ].concat(r)) === !1 || n.isDefaultPrevented());
        }
    }, e.each({
        show: "fadeIn",
        hide: "fadeOut"
    }, function(t, n) {
        e.Widget.prototype["_" + t] = function(r, i, s) {
            "string" == typeof i && (i = {
                effect: i
            });
            var o, u = i ? i === !0 || "number" == typeof i ? n : i.effect || n : t;
            i = i || {}, "number" == typeof i && (i = {
                duration: i
            }), o = !e.isEmptyObject(i), i.complete = s, i.delay && r.delay(i.delay), o && e.effects && e.effects.effect[u] ? r[t](i) : u !== t && r[u] ? r[u](i.duration, i.easing, s) : r.queue(function(n) {
                e(this)[t](), s && s.call(r[0]), n();
            });
        };
    });
})(jQuery);

(function(e) {
    var t = !1;
    e(document).mouseup(function() {
        t = !1;
    }), e.widget("ui.mouse", {
        version: "1.10.3",
        options: {
            cancel: "input,textarea,button,select,option",
            distance: 1,
            delay: 0
        },
        _mouseInit: function() {
            var t = this;
            this.element.bind("mousedown." + this.widgetName, function(e) {
                return t._mouseDown(e);
            }).bind("click." + this.widgetName, function(n) {
                return !0 === e.data(n.target, t.widgetName + ".preventClickEvent") ? (e.removeData(n.target, t.widgetName + ".preventClickEvent"), n.stopImmediatePropagation(), !1) : undefined;
            }), this.started = !1;
        },
        _mouseDestroy: function() {
            this.element.unbind("." + this.widgetName), this._mouseMoveDelegate && e(document).unbind("mousemove." + this.widgetName, this._mouseMoveDelegate).unbind("mouseup." + this.widgetName, this._mouseUpDelegate);
        },
        _mouseDown: function(n) {
            if (!t) {
                this._mouseStarted && this._mouseUp(n), this._mouseDownEvent = n;
                var r = this, i = 1 === n.which, s = "string" == typeof this.options.cancel && n.target.nodeName ? e(n.target).closest(this.options.cancel).length : !1;
                return i && !s && this._mouseCapture(n) ? (this.mouseDelayMet = !this.options.delay, this.mouseDelayMet || (this._mouseDelayTimer = setTimeout(function() {
                    r.mouseDelayMet = !0;
                }, this.options.delay)), this._mouseDistanceMet(n) && this._mouseDelayMet(n) && (this._mouseStarted = this._mouseStart(n) !== !1, !this._mouseStarted) ? (n.preventDefault(), !0) : (!0 === e.data(n.target, this.widgetName + ".preventClickEvent") && e.removeData(n.target, this.widgetName + ".preventClickEvent"), this._mouseMoveDelegate = function(e) {
                    return r._mouseMove(e);
                }, this._mouseUpDelegate = function(e) {
                    return r._mouseUp(e);
                }, e(document).bind("mousemove." + this.widgetName, this._mouseMoveDelegate).bind("mouseup." + this.widgetName, this._mouseUpDelegate), n.preventDefault(), t = !0, !0)) : !0;
            }
        },
        _mouseMove: function(t) {
            return e.ui.ie && (!document.documentMode || 9 > document.documentMode) && !t.button ? this._mouseUp(t) : this._mouseStarted ? (this._mouseDrag(t), t.preventDefault()) : (this._mouseDistanceMet(t) && this._mouseDelayMet(t) && (this._mouseStarted = this._mouseStart(this._mouseDownEvent, t) !== !1, this._mouseStarted ? this._mouseDrag(t) : this._mouseUp(t)), !this._mouseStarted);
        },
        _mouseUp: function(t) {
            return e(document).unbind("mousemove." + this.widgetName, this._mouseMoveDelegate).unbind("mouseup." + this.widgetName, this._mouseUpDelegate), this._mouseStarted && (this._mouseStarted = !1, t.target === this._mouseDownEvent.target && e.data(t.target, this.widgetName + ".preventClickEvent", !0), this._mouseStop(t)), !1;
        },
        _mouseDistanceMet: function(e) {
            return Math.max(Math.abs(this._mouseDownEvent.pageX - e.pageX), Math.abs(this._mouseDownEvent.pageY - e.pageY)) >= this.options.distance;
        },
        _mouseDelayMet: function() {
            return this.mouseDelayMet;
        },
        _mouseStart: function() {},
        _mouseDrag: function() {},
        _mouseStop: function() {},
        _mouseCapture: function() {
            return !0;
        }
    });
})(jQuery);

(function(e, t) {
    function n(e, t, n) {
        return [ parseFloat(e[0]) * (p.test(e[0]) ? t / 100 : 1), parseFloat(e[1]) * (p.test(e[1]) ? n / 100 : 1) ];
    }
    function r(t, n) {
        return parseInt(e.css(t, n), 10) || 0;
    }
    function i(t) {
        var n = t[0];
        return 9 === n.nodeType ? {
            width: t.width(),
            height: t.height(),
            offset: {
                top: 0,
                left: 0
            }
        } : e.isWindow(n) ? {
            width: t.width(),
            height: t.height(),
            offset: {
                top: t.scrollTop(),
                left: t.scrollLeft()
            }
        } : n.preventDefault ? {
            width: 0,
            height: 0,
            offset: {
                top: n.pageY,
                left: n.pageX
            }
        } : {
            width: t.outerWidth(),
            height: t.outerHeight(),
            offset: t.offset()
        };
    }
    e.ui = e.ui || {};
    var s, o = Math.max, u = Math.abs, a = Math.round, f = /left|center|right/, l = /top|center|bottom/, c = /[\+\-]\d+(\.[\d]+)?%?/, h = /^\w+/, p = /%$/, d = e.fn.position;
    e.position = {
        scrollbarWidth: function() {
            if (s !== t) return s;
            var n, r, i = e("<div style='display:block;width:50px;height:50px;overflow:hidden;'><div style='height:100px;width:auto;'></div></div>"), o = i.children()[0];
            return e("body").append(i), n = o.offsetWidth, i.css("overflow", "scroll"), r = o.offsetWidth, n === r && (r = i[0].clientWidth), i.remove(), s = n - r;
        },
        getScrollInfo: function(t) {
            var n = t.isWindow ? "" : t.element.css("overflow-x"), r = t.isWindow ? "" : t.element.css("overflow-y"), i = "scroll" === n || "auto" === n && t.width < t.element[0].scrollWidth, s = "scroll" === r || "auto" === r && t.height < t.element[0].scrollHeight;
            return {
                width: s ? e.position.scrollbarWidth() : 0,
                height: i ? e.position.scrollbarWidth() : 0
            };
        },
        getWithinInfo: function(t) {
            var n = e(t || window), r = e.isWindow(n[0]);
            return {
                element: n,
                isWindow: r,
                offset: n.offset() || {
                    left: 0,
                    top: 0
                },
                scrollLeft: n.scrollLeft(),
                scrollTop: n.scrollTop(),
                width: r ? n.width() : n.outerWidth(),
                height: r ? n.height() : n.outerHeight()
            };
        }
    }, e.fn.position = function(t) {
        if (!t || !t.of) return d.apply(this, arguments);
        t = e.extend({}, t);
        var s, p, v, m, g, y, b = e(t.of), w = e.position.getWithinInfo(t.within), E = e.position.getScrollInfo(w), S = (t.collision || "flip").split(" "), x = {};
        return y = i(b), b[0].preventDefault && (t.at = "left top"), p = y.width, v = y.height, m = y.offset, g = e.extend({}, m), e.each([ "my", "at" ], function() {
            var e, n, r = (t[this] || "").split(" ");
            1 === r.length && (r = f.test(r[0]) ? r.concat([ "center" ]) : l.test(r[0]) ? [ "center" ].concat(r) : [ "center", "center" ]), r[0] = f.test(r[0]) ? r[0] : "center", r[1] = l.test(r[1]) ? r[1] : "center", e = c.exec(r[0]), n = c.exec(r[1]), x[this] = [ e ? e[0] : 0, n ? n[0] : 0 ], t[this] = [ h.exec(r[0])[0], h.exec(r[1])[0] ];
        }), 1 === S.length && (S[1] = S[0]), "right" === t.at[0] ? g.left += p : "center" === t.at[0] && (g.left += p / 2), "bottom" === t.at[1] ? g.top += v : "center" === t.at[1] && (g.top += v / 2), s = n(x.at, p, v), g.left += s[0], g.top += s[1], this.each(function() {
            var i, f, l = e(this), c = l.outerWidth(), h = l.outerHeight(), d = r(this, "marginLeft"), y = r(this, "marginTop"), T = c + d + r(this, "marginRight") + E.width, N = h + y + r(this, "marginBottom") + E.height, C = e.extend({}, g), k = n(x.my, l.outerWidth(), l.outerHeight());
            "right" === t.my[0] ? C.left -= c : "center" === t.my[0] && (C.left -= c / 2), "bottom" === t.my[1] ? C.top -= h : "center" === t.my[1] && (C.top -= h / 2), C.left += k[0], C.top += k[1], e.support.offsetFractions || (C.left = a(C.left), C.top = a(C.top)), i = {
                marginLeft: d,
                marginTop: y
            }, e.each([ "left", "top" ], function(n, r) {
                e.ui.position[S[n]] && e.ui.position[S[n]][r](C, {
                    targetWidth: p,
                    targetHeight: v,
                    elemWidth: c,
                    elemHeight: h,
                    collisionPosition: i,
                    collisionWidth: T,
                    collisionHeight: N,
                    offset: [ s[0] + k[0], s[1] + k[1] ],
                    my: t.my,
                    at: t.at,
                    within: w,
                    elem: l
                });
            }), t.using && (f = function(e) {
                var n = m.left - C.left, r = n + p - c, i = m.top - C.top, s = i + v - h, a = {
                    target: {
                        element: b,
                        left: m.left,
                        top: m.top,
                        width: p,
                        height: v
                    },
                    element: {
                        element: l,
                        left: C.left,
                        top: C.top,
                        width: c,
                        height: h
                    },
                    horizontal: 0 > r ? "left" : n > 0 ? "right" : "center",
                    vertical: 0 > s ? "top" : i > 0 ? "bottom" : "middle"
                };
                c > p && p > u(n + r) && (a.horizontal = "center"), h > v && v > u(i + s) && (a.vertical = "middle"), a.important = o(u(n), u(r)) > o(u(i), u(s)) ? "horizontal" : "vertical", t.using.call(this, e, a);
            }), l.offset(e.extend(C, {
                using: f
            }));
        });
    }, e.ui.position = {
        fit: {
            left: function(e, t) {
                var n, r = t.within, i = r.isWindow ? r.scrollLeft : r.offset.left, s = r.width, u = e.left - t.collisionPosition.marginLeft, a = i - u, f = u + t.collisionWidth - s - i;
                t.collisionWidth > s ? a > 0 && 0 >= f ? (n = e.left + a + t.collisionWidth - s - i, e.left += a - n) : e.left = f > 0 && 0 >= a ? i : a > f ? i + s - t.collisionWidth : i : a > 0 ? e.left += a : f > 0 ? e.left -= f : e.left = o(e.left - u, e.left);
            },
            top: function(e, t) {
                var n, r = t.within, i = r.isWindow ? r.scrollTop : r.offset.top, s = t.within.height, u = e.top - t.collisionPosition.marginTop, a = i - u, f = u + t.collisionHeight - s - i;
                t.collisionHeight > s ? a > 0 && 0 >= f ? (n = e.top + a + t.collisionHeight - s - i, e.top += a - n) : e.top = f > 0 && 0 >= a ? i : a > f ? i + s - t.collisionHeight : i : a > 0 ? e.top += a : f > 0 ? e.top -= f : e.top = o(e.top - u, e.top);
            }
        },
        flip: {
            left: function(e, t) {
                var n, r, i = t.within, s = i.offset.left + i.scrollLeft, o = i.width, a = i.isWindow ? i.scrollLeft : i.offset.left, f = e.left - t.collisionPosition.marginLeft, l = f - a, c = f + t.collisionWidth - o - a, h = "left" === t.my[0] ? -t.elemWidth : "right" === t.my[0] ? t.elemWidth : 0, p = "left" === t.at[0] ? t.targetWidth : "right" === t.at[0] ? -t.targetWidth : 0, d = -2 * t.offset[0];
                0 > l ? (n = e.left + h + p + d + t.collisionWidth - o - s, (0 > n || u(l) > n) && (e.left += h + p + d)) : c > 0 && (r = e.left - t.collisionPosition.marginLeft + h + p + d - a, (r > 0 || c > u(r)) && (e.left += h + p + d));
            },
            top: function(e, t) {
                var n, r, i = t.within, s = i.offset.top + i.scrollTop, o = i.height, a = i.isWindow ? i.scrollTop : i.offset.top, f = e.top - t.collisionPosition.marginTop, l = f - a, c = f + t.collisionHeight - o - a, h = "top" === t.my[1], p = h ? -t.elemHeight : "bottom" === t.my[1] ? t.elemHeight : 0, d = "top" === t.at[1] ? t.targetHeight : "bottom" === t.at[1] ? -t.targetHeight : 0, v = -2 * t.offset[1];
                0 > l ? (r = e.top + p + d + v + t.collisionHeight - o - s, e.top + p + d + v > l && (0 > r || u(l) > r) && (e.top += p + d + v)) : c > 0 && (n = e.top - t.collisionPosition.marginTop + p + d + v - a, e.top + p + d + v > c && (n > 0 || c > u(n)) && (e.top += p + d + v));
            }
        },
        flipfit: {
            left: function() {
                e.ui.position.flip.left.apply(this, arguments), e.ui.position.fit.left.apply(this, arguments);
            },
            top: function() {
                e.ui.position.flip.top.apply(this, arguments), e.ui.position.fit.top.apply(this, arguments);
            }
        }
    }, function() {
        var t, n, r, i, s, o = document.getElementsByTagName("body")[0], u = document.createElement("div");
        t = document.createElement(o ? "div" : "body"), r = {
            visibility: "hidden",
            width: 0,
            height: 0,
            border: 0,
            margin: 0,
            background: "none"
        }, o && e.extend(r, {
            position: "absolute",
            left: "-1000px",
            top: "-1000px"
        });
        for (s in r) t.style[s] = r[s];
        t.appendChild(u), n = o || document.documentElement, n.insertBefore(t, n.firstChild), u.style.cssText = "position: absolute; left: 10.7432222px;", i = e(u).offset().left, e.support.offsetFractions = i > 10 && 11 > i, t.innerHTML = "", n.removeChild(t);
    }();
})(jQuery);

(function(e) {
    e.widget("ui.draggable", e.ui.mouse, {
        version: "1.10.3",
        widgetEventPrefix: "drag",
        options: {
            addClasses: !0,
            appendTo: "parent",
            axis: !1,
            connectToSortable: !1,
            containment: !1,
            cursor: "auto",
            cursorAt: !1,
            grid: !1,
            handle: !1,
            helper: "original",
            iframeFix: !1,
            opacity: !1,
            refreshPositions: !1,
            revert: !1,
            revertDuration: 500,
            scope: "default",
            scroll: !0,
            scrollSensitivity: 20,
            scrollSpeed: 20,
            snap: !1,
            snapMode: "both",
            snapTolerance: 20,
            stack: !1,
            zIndex: !1,
            drag: null,
            start: null,
            stop: null
        },
        _create: function() {
            "original" !== this.options.helper || /^(?:r|a|f)/.test(this.element.css("position")) || (this.element[0].style.position = "relative"), this.options.addClasses && this.element.addClass("ui-draggable"), this.options.disabled && this.element.addClass("ui-draggable-disabled"), this._mouseInit();
        },
        _destroy: function() {
            this.element.removeClass("ui-draggable ui-draggable-dragging ui-draggable-disabled"), this._mouseDestroy();
        },
        _mouseCapture: function(n) {
            var r = this.options;
            return this.helper || r.disabled || e(n.target).closest(".ui-resizable-handle").length > 0 ? !1 : (this.handle = this._getHandle(n), this.handle ? (e(r.iframeFix === !0 ? "iframe" : r.iframeFix).each(function() {
                e("<div class='ui-draggable-iframeFix' style='background: #fff;'></div>").css({
                    width: this.offsetWidth + "px",
                    height: this.offsetHeight + "px",
                    position: "absolute",
                    opacity: "0.001",
                    zIndex: 1e3
                }).css(e(this).offset()).appendTo("body");
            }), !0) : !1);
        },
        _mouseStart: function(n) {
            var r = this.options;
            return this.helper = this._createHelper(n), this.helper.addClass("ui-draggable-dragging"), this._cacheHelperProportions(), e.ui.ddmanager && (e.ui.ddmanager.current = this), this._cacheMargins(), this.cssPosition = this.helper.css("position"), this.scrollParent = this.helper.scrollParent(), this.offsetParent = this.helper.offsetParent(), this.offsetParentCssPosition = this.offsetParent.css("position"), this.offset = this.positionAbs = this.element.offset(), this.offset = {
                top: this.offset.top - this.margins.top,
                left: this.offset.left - this.margins.left
            }, this.offset.scroll = !1, e.extend(this.offset, {
                click: {
                    left: n.pageX - this.offset.left,
                    top: n.pageY - this.offset.top
                },
                parent: this._getParentOffset(),
                relative: this._getRelativeOffset()
            }), this.originalPosition = this.position = this._generatePosition(n), this.originalPageX = n.pageX, this.originalPageY = n.pageY, r.cursorAt && this._adjustOffsetFromHelper(r.cursorAt), this._setContainment(), this._trigger("start", n) === !1 ? (this._clear(), !1) : (this._cacheHelperProportions(), e.ui.ddmanager && !r.dropBehaviour && e.ui.ddmanager.prepareOffsets(this, n), this._mouseDrag(n, !0), e.ui.ddmanager && e.ui.ddmanager.dragStart(this, n), !0);
        },
        _mouseDrag: function(n, r) {
            if ("fixed" === this.offsetParentCssPosition && (this.offset.parent = this._getParentOffset()), this.position = this._generatePosition(n), this.positionAbs = this._convertPositionTo("absolute"), !r) {
                var i = this._uiHash();
                if (this._trigger("drag", n, i) === !1) return this._mouseUp({}), !1;
                this.position = i.position;
            }
            return this.options.axis && "y" === this.options.axis || (this.helper[0].style.left = this.position.left + "px"), this.options.axis && "x" === this.options.axis || (this.helper[0].style.top = this.position.top + "px"), e.ui.ddmanager && e.ui.ddmanager.drag(this, n), !1;
        },
        _mouseStop: function(n) {
            var r = this, i = !1;
            return e.ui.ddmanager && !this.options.dropBehaviour && (i = e.ui.ddmanager.drop(this, n)), this.dropped && (i = this.dropped, this.dropped = !1), "original" !== this.options.helper || e.contains(this.element[0].ownerDocument, this.element[0]) ? ("invalid" === this.options.revert && !i || "valid" === this.options.revert && i || this.options.revert === !0 || e.isFunction(this.options.revert) && this.options.revert.call(this.element, i) ? e(this.helper).animate(this.originalPosition, parseInt(this.options.revertDuration, 10), function() {
                r._trigger("stop", n) !== !1 && r._clear();
            }) : this._trigger("stop", n) !== !1 && this._clear(), !1) : !1;
        },
        _mouseUp: function(n) {
            return e("div.ui-draggable-iframeFix").each(function() {
                this.parentNode.removeChild(this);
            }), e.ui.ddmanager && e.ui.ddmanager.dragStop(this, n), e.ui.mouse.prototype._mouseUp.call(this, n);
        },
        cancel: function() {
            return this.helper.is(".ui-draggable-dragging") ? this._mouseUp({}) : this._clear(), this;
        },
        _getHandle: function(n) {
            return this.options.handle ? !!e(n.target).closest(this.element.find(this.options.handle)).length : !0;
        },
        _createHelper: function(n) {
            var r = this.options, i = e.isFunction(r.helper) ? e(r.helper.apply(this.element[0], [ n ])) : "clone" === r.helper ? this.element.clone().removeAttr("id") : this.element;
            return i.parents("body").length || i.appendTo("parent" === r.appendTo ? this.element[0].parentNode : r.appendTo), i[0] === this.element[0] || /(fixed|absolute)/.test(i.css("position")) || i.css("position", "absolute"), i;
        },
        _adjustOffsetFromHelper: function(n) {
            "string" == typeof n && (n = n.split(" ")), e.isArray(n) && (n = {
                left: +n[0],
                top: +n[1] || 0
            }), "left" in n && (this.offset.click.left = n.left + this.margins.left), "right" in n && (this.offset.click.left = this.helperProportions.width - n.right + this.margins.left), "top" in n && (this.offset.click.top = n.top + this.margins.top), "bottom" in n && (this.offset.click.top = this.helperProportions.height - n.bottom + this.margins.top);
        },
        _getParentOffset: function() {
            var n = this.offsetParent.offset();
            return "absolute" === this.cssPosition && this.scrollParent[0] !== document && e.contains(this.scrollParent[0], this.offsetParent[0]) && (n.left += this.scrollParent.scrollLeft(), n.top += this.scrollParent.scrollTop()), (this.offsetParent[0] === document.body || this.offsetParent[0].tagName && "html" === this.offsetParent[0].tagName.toLowerCase() && e.ui.ie) && (n = {
                top: 0,
                left: 0
            }), {
                top: n.top + (parseInt(this.offsetParent.css("borderTopWidth"), 10) || 0),
                left: n.left + (parseInt(this.offsetParent.css("borderLeftWidth"), 10) || 0)
            };
        },
        _getRelativeOffset: function() {
            if ("relative" === this.cssPosition) {
                var e = this.element.position();
                return {
                    top: e.top - (parseInt(this.helper.css("top"), 10) || 0) + this.scrollParent.scrollTop(),
                    left: e.left - (parseInt(this.helper.css("left"), 10) || 0) + this.scrollParent.scrollLeft()
                };
            }
            return {
                top: 0,
                left: 0
            };
        },
        _cacheMargins: function() {
            this.margins = {
                left: parseInt(this.element.css("marginLeft"), 10) || 0,
                top: parseInt(this.element.css("marginTop"), 10) || 0,
                right: parseInt(this.element.css("marginRight"), 10) || 0,
                bottom: parseInt(this.element.css("marginBottom"), 10) || 0
            };
        },
        _cacheHelperProportions: function() {
            this.helperProportions = {
                width: this.helper.outerWidth(),
                height: this.helper.outerHeight()
            };
        },
        _setContainment: function() {
            var n, r, i, s = this.options;
            return s.containment ? "window" === s.containment ? (this.containment = [ e(window).scrollLeft() - this.offset.relative.left - this.offset.parent.left, e(window).scrollTop() - this.offset.relative.top - this.offset.parent.top, e(window).scrollLeft() + e(window).width() - this.helperProportions.width - this.margins.left, e(window).scrollTop() + (e(window).height() || document.body.parentNode.scrollHeight) - this.helperProportions.height - this.margins.top ], undefined) : "document" === s.containment ? (this.containment = [ 0, 0, e(document).width() - this.helperProportions.width - this.margins.left, (e(document).height() || document.body.parentNode.scrollHeight) - this.helperProportions.height - this.margins.top ], undefined) : s.containment.constructor === Array ? (this.containment = s.containment, undefined) : ("parent" === s.containment && (s.containment = this.helper[0].parentNode), r = e(s.containment), i = r[0], i && (n = "hidden" !== r.css("overflow"), this.containment = [ (parseInt(r.css("borderLeftWidth"), 10) || 0) + (parseInt(r.css("paddingLeft"), 10) || 0), (parseInt(r.css("borderTopWidth"), 10) || 0) + (parseInt(r.css("paddingTop"), 10) || 0), (n ? Math.max(i.scrollWidth, i.offsetWidth) : i.offsetWidth) - (parseInt(r.css("borderRightWidth"), 10) || 0) - (parseInt(r.css("paddingRight"), 10) || 0) - this.helperProportions.width - this.margins.left - this.margins.right, (n ? Math.max(i.scrollHeight, i.offsetHeight) : i.offsetHeight) - (parseInt(r.css("borderBottomWidth"), 10) || 0) - (parseInt(r.css("paddingBottom"), 10) || 0) - this.helperProportions.height - this.margins.top - this.margins.bottom ], this.relative_container = r), undefined) : (this.containment = null, undefined);
        },
        _convertPositionTo: function(n, r) {
            r || (r = this.position);
            var i = "absolute" === n ? 1 : -1, s = "absolute" !== this.cssPosition || this.scrollParent[0] !== document && e.contains(this.scrollParent[0], this.offsetParent[0]) ? this.scrollParent : this.offsetParent;
            return this.offset.scroll || (this.offset.scroll = {
                top: s.scrollTop(),
                left: s.scrollLeft()
            }), {
                top: r.top + this.offset.relative.top * i + this.offset.parent.top * i - ("fixed" === this.cssPosition ? -this.scrollParent.scrollTop() : this.offset.scroll.top) * i,
                left: r.left + this.offset.relative.left * i + this.offset.parent.left * i - ("fixed" === this.cssPosition ? -this.scrollParent.scrollLeft() : this.offset.scroll.left) * i
            };
        },
        _generatePosition: function(n) {
            var r, i, s, o, u = this.options, a = "absolute" !== this.cssPosition || this.scrollParent[0] !== document && e.contains(this.scrollParent[0], this.offsetParent[0]) ? this.scrollParent : this.offsetParent, f = n.pageX, l = n.pageY;
            return this.offset.scroll || (this.offset.scroll = {
                top: a.scrollTop(),
                left: a.scrollLeft()
            }), this.originalPosition && (this.containment && (this.relative_container ? (i = this.relative_container.offset(), r = [ this.containment[0] + i.left, this.containment[1] + i.top, this.containment[2] + i.left, this.containment[3] + i.top ]) : r = this.containment, n.pageX - this.offset.click.left < r[0] && (f = r[0] + this.offset.click.left), n.pageY - this.offset.click.top < r[1] && (l = r[1] + this.offset.click.top), n.pageX - this.offset.click.left > r[2] && (f = r[2] + this.offset.click.left), n.pageY - this.offset.click.top > r[3] && (l = r[3] + this.offset.click.top)), u.grid && (s = u.grid[1] ? this.originalPageY + Math.round((l - this.originalPageY) / u.grid[1]) * u.grid[1] : this.originalPageY, l = r ? s - this.offset.click.top >= r[1] || s - this.offset.click.top > r[3] ? s : s - this.offset.click.top >= r[1] ? s - u.grid[1] : s + u.grid[1] : s, o = u.grid[0] ? this.originalPageX + Math.round((f - this.originalPageX) / u.grid[0]) * u.grid[0] : this.originalPageX, f = r ? o - this.offset.click.left >= r[0] || o - this.offset.click.left > r[2] ? o : o - this.offset.click.left >= r[0] ? o - u.grid[0] : o + u.grid[0] : o)), {
                top: l - this.offset.click.top - this.offset.relative.top - this.offset.parent.top + ("fixed" === this.cssPosition ? -this.scrollParent.scrollTop() : this.offset.scroll.top),
                left: f - this.offset.click.left - this.offset.relative.left - this.offset.parent.left + ("fixed" === this.cssPosition ? -this.scrollParent.scrollLeft() : this.offset.scroll.left)
            };
        },
        _clear: function() {
            this.helper.removeClass("ui-draggable-dragging"), this.helper[0] === this.element[0] || this.cancelHelperRemoval || this.helper.remove(), this.helper = null, this.cancelHelperRemoval = !1;
        },
        _trigger: function(n, r, i) {
            return i = i || this._uiHash(), e.ui.plugin.call(this, n, [ r, i ]), "drag" === n && (this.positionAbs = this._convertPositionTo("absolute")), e.Widget.prototype._trigger.call(this, n, r, i);
        },
        plugins: {},
        _uiHash: function() {
            return {
                helper: this.helper,
                position: this.position,
                originalPosition: this.originalPosition,
                offset: this.positionAbs
            };
        }
    }), e.ui.plugin.add("draggable", "connectToSortable", {
        start: function(n, r) {
            var i = e(this).data("ui-draggable"), s = i.options, o = e.extend({}, r, {
                item: i.element
            });
            i.sortables = [], e(s.connectToSortable).each(function() {
                var r = e.data(this, "ui-sortable");
                r && !r.options.disabled && (i.sortables.push({
                    instance: r,
                    shouldRevert: r.options.revert
                }), r.refreshPositions(), r._trigger("activate", n, o));
            });
        },
        stop: function(n, r) {
            var i = e(this).data("ui-draggable"), s = e.extend({}, r, {
                item: i.element
            });
            e.each(i.sortables, function() {
                this.instance.isOver ? (this.instance.isOver = 0, i.cancelHelperRemoval = !0, this.instance.cancelHelperRemoval = !1, this.shouldRevert && (this.instance.options.revert = this.shouldRevert), this.instance._mouseStop(n), this.instance.options.helper = this.instance.options._helper, "original" === i.options.helper && this.instance.currentItem.css({
                    top: "auto",
                    left: "auto"
                })) : (this.instance.cancelHelperRemoval = !1, this.instance._trigger("deactivate", n, s));
            });
        },
        drag: function(n, r) {
            var i = e(this).data("ui-draggable"), s = this;
            e.each(i.sortables, function() {
                var o = !1, u = this;
                this.instance.positionAbs = i.positionAbs, this.instance.helperProportions = i.helperProportions, this.instance.offset.click = i.offset.click, this.instance._intersectsWith(this.instance.containerCache) && (o = !0, e.each(i.sortables, function() {
                    return this.instance.positionAbs = i.positionAbs, this.instance.helperProportions = i.helperProportions, this.instance.offset.click = i.offset.click, this !== u && this.instance._intersectsWith(this.instance.containerCache) && e.contains(u.instance.element[0], this.instance.element[0]) && (o = !1), o;
                })), o ? (this.instance.isOver || (this.instance.isOver = 1, this.instance.currentItem = e(s).clone().removeAttr("id").appendTo(this.instance.element).data("ui-sortable-item", !0), this.instance.options._helper = this.instance.options.helper, this.instance.options.helper = function() {
                    return r.helper[0];
                }, n.target = this.instance.currentItem[0], this.instance._mouseCapture(n, !0), this.instance._mouseStart(n, !0, !0), this.instance.offset.click.top = i.offset.click.top, this.instance.offset.click.left = i.offset.click.left, this.instance.offset.parent.left -= i.offset.parent.left - this.instance.offset.parent.left, this.instance.offset.parent.top -= i.offset.parent.top - this.instance.offset.parent.top, i._trigger("toSortable", n), i.dropped = this.instance.element, i.currentItem = i.element, this.instance.fromOutside = i), this.instance.currentItem && this.instance._mouseDrag(n)) : this.instance.isOver && (this.instance.isOver = 0, this.instance.cancelHelperRemoval = !0, this.instance.options.revert = !1, this.instance._trigger("out", n, this.instance._uiHash(this.instance)), this.instance._mouseStop(n, !0), this.instance.options.helper = this.instance.options._helper, this.instance.currentItem.remove(), this.instance.placeholder && this.instance.placeholder.remove(), i._trigger("fromSortable", n), i.dropped = !1);
            });
        }
    }), e.ui.plugin.add("draggable", "cursor", {
        start: function() {
            var n = e("body"), r = e(this).data("ui-draggable").options;
            n.css("cursor") && (r._cursor = n.css("cursor")), n.css("cursor", r.cursor);
        },
        stop: function() {
            var n = e(this).data("ui-draggable").options;
            n._cursor && e("body").css("cursor", n._cursor);
        }
    }), e.ui.plugin.add("draggable", "opacity", {
        start: function(n, r) {
            var i = e(r.helper), s = e(this).data("ui-draggable").options;
            i.css("opacity") && (s._opacity = i.css("opacity")), i.css("opacity", s.opacity);
        },
        stop: function(n, r) {
            var i = e(this).data("ui-draggable").options;
            i._opacity && e(r.helper).css("opacity", i._opacity);
        }
    }), e.ui.plugin.add("draggable", "scroll", {
        start: function() {
            var n = e(this).data("ui-draggable");
            n.scrollParent[0] !== document && "HTML" !== n.scrollParent[0].tagName && (n.overflowOffset = n.scrollParent.offset());
        },
        drag: function(n) {
            var r = e(this).data("ui-draggable"), i = r.options, s = !1;
            r.scrollParent[0] !== document && "HTML" !== r.scrollParent[0].tagName ? (i.axis && "x" === i.axis || (r.overflowOffset.top + r.scrollParent[0].offsetHeight - n.pageY < i.scrollSensitivity ? r.scrollParent[0].scrollTop = s = r.scrollParent[0].scrollTop + i.scrollSpeed : n.pageY - r.overflowOffset.top < i.scrollSensitivity && (r.scrollParent[0].scrollTop = s = r.scrollParent[0].scrollTop - i.scrollSpeed)), i.axis && "y" === i.axis || (r.overflowOffset.left + r.scrollParent[0].offsetWidth - n.pageX < i.scrollSensitivity ? r.scrollParent[0].scrollLeft = s = r.scrollParent[0].scrollLeft + i.scrollSpeed : n.pageX - r.overflowOffset.left < i.scrollSensitivity && (r.scrollParent[0].scrollLeft = s = r.scrollParent[0].scrollLeft - i.scrollSpeed))) : (i.axis && "x" === i.axis || (n.pageY - e(document).scrollTop() < i.scrollSensitivity ? s = e(document).scrollTop(e(document).scrollTop() - i.scrollSpeed) : e(window).height() - (n.pageY - e(document).scrollTop()) < i.scrollSensitivity && (s = e(document).scrollTop(e(document).scrollTop() + i.scrollSpeed))), i.axis && "y" === i.axis || (n.pageX - e(document).scrollLeft() < i.scrollSensitivity ? s = e(document).scrollLeft(e(document).scrollLeft() - i.scrollSpeed) : e(window).width() - (n.pageX - e(document).scrollLeft()) < i.scrollSensitivity && (s = e(document).scrollLeft(e(document).scrollLeft() + i.scrollSpeed)))), s !== !1 && e.ui.ddmanager && !i.dropBehaviour && e.ui.ddmanager.prepareOffsets(r, n);
        }
    }), e.ui.plugin.add("draggable", "snap", {
        start: function() {
            var n = e(this).data("ui-draggable"), r = n.options;
            n.snapElements = [], e(r.snap.constructor !== String ? r.snap.items || ":data(ui-draggable)" : r.snap).each(function() {
                var r = e(this), i = r.offset();
                this !== n.element[0] && n.snapElements.push({
                    item: this,
                    width: r.outerWidth(),
                    height: r.outerHeight(),
                    top: i.top,
                    left: i.left
                });
            });
        },
        drag: function(n, r) {
            var i, s, o, u, a, f, l, c, h, p, d = e(this).data("ui-draggable"), v = d.options, m = v.snapTolerance, g = r.offset.left, y = g + d.helperProportions.width, b = r.offset.top, w = b + d.helperProportions.height;
            for (h = d.snapElements.length - 1; h >= 0; h--) a = d.snapElements[h].left, f = a + d.snapElements[h].width, l = d.snapElements[h].top, c = l + d.snapElements[h].height, a - m > y || g > f + m || l - m > w || b > c + m || !e.contains(d.snapElements[h].item.ownerDocument, d.snapElements[h].item) ? (d.snapElements[h].snapping && d.options.snap.release && d.options.snap.release.call(d.element, n, e.extend(d._uiHash(), {
                snapItem: d.snapElements[h].item
            })), d.snapElements[h].snapping = !1) : ("inner" !== v.snapMode && (i = m >= Math.abs(l - w), s = m >= Math.abs(c - b), o = m >= Math.abs(a - y), u = m >= Math.abs(f - g), i && (r.position.top = d._convertPositionTo("relative", {
                top: l - d.helperProportions.height,
                left: 0
            }).top - d.margins.top), s && (r.position.top = d._convertPositionTo("relative", {
                top: c,
                left: 0
            }).top - d.margins.top), o && (r.position.left = d._convertPositionTo("relative", {
                top: 0,
                left: a - d.helperProportions.width
            }).left - d.margins.left), u && (r.position.left = d._convertPositionTo("relative", {
                top: 0,
                left: f
            }).left - d.margins.left)), p = i || s || o || u, "outer" !== v.snapMode && (i = m >= Math.abs(l - b), s = m >= Math.abs(c - w), o = m >= Math.abs(a - g), u = m >= Math.abs(f - y), i && (r.position.top = d._convertPositionTo("relative", {
                top: l,
                left: 0
            }).top - d.margins.top), s && (r.position.top = d._convertPositionTo("relative", {
                top: c - d.helperProportions.height,
                left: 0
            }).top - d.margins.top), o && (r.position.left = d._convertPositionTo("relative", {
                top: 0,
                left: a
            }).left - d.margins.left), u && (r.position.left = d._convertPositionTo("relative", {
                top: 0,
                left: f - d.helperProportions.width
            }).left - d.margins.left)), !d.snapElements[h].snapping && (i || s || o || u || p) && d.options.snap.snap && d.options.snap.snap.call(d.element, n, e.extend(d._uiHash(), {
                snapItem: d.snapElements[h].item
            })), d.snapElements[h].snapping = i || s || o || u || p);
        }
    }), e.ui.plugin.add("draggable", "stack", {
        start: function() {
            var n, r = this.data("ui-draggable").options, i = e.makeArray(e(r.stack)).sort(function(n, r) {
                return (parseInt(e(n).css("zIndex"), 10) || 0) - (parseInt(e(r).css("zIndex"), 10) || 0);
            });
            i.length && (n = parseInt(e(i[0]).css("zIndex"), 10) || 0, e(i).each(function(r) {
                e(this).css("zIndex", n + r);
            }), this.css("zIndex", n + i.length));
        }
    }), e.ui.plugin.add("draggable", "zIndex", {
        start: function(n, r) {
            var i = e(r.helper), s = e(this).data("ui-draggable").options;
            i.css("zIndex") && (s._zIndex = i.css("zIndex")), i.css("zIndex", s.zIndex);
        },
        stop: function(n, r) {
            var i = e(this).data("ui-draggable").options;
            i._zIndex && e(r.helper).css("zIndex", i._zIndex);
        }
    });
})(jQuery);

(function(e) {
    function t(e, t, n) {
        return e > t && t + n > e;
    }
    e.widget("ui.droppable", {
        version: "1.10.3",
        widgetEventPrefix: "drop",
        options: {
            accept: "*",
            activeClass: !1,
            addClasses: !0,
            greedy: !1,
            hoverClass: !1,
            scope: "default",
            tolerance: "intersect",
            activate: null,
            deactivate: null,
            drop: null,
            out: null,
            over: null
        },
        _create: function() {
            var t = this.options, n = t.accept;
            this.isover = !1, this.isout = !0, this.accept = e.isFunction(n) ? n : function(e) {
                return e.is(n);
            }, this.proportions = {
                width: this.element[0].offsetWidth,
                height: this.element[0].offsetHeight
            }, e.ui.ddmanager.droppables[t.scope] = e.ui.ddmanager.droppables[t.scope] || [], e.ui.ddmanager.droppables[t.scope].push(this), t.addClasses && this.element.addClass("ui-droppable");
        },
        _destroy: function() {
            for (var t = 0, n = e.ui.ddmanager.droppables[this.options.scope]; n.length > t; t++) n[t] === this && n.splice(t, 1);
            this.element.removeClass("ui-droppable ui-droppable-disabled");
        },
        _setOption: function(t, n) {
            "accept" === t && (this.accept = e.isFunction(n) ? n : function(e) {
                return e.is(n);
            }), e.Widget.prototype._setOption.apply(this, arguments);
        },
        _activate: function(t) {
            var n = e.ui.ddmanager.current;
            this.options.activeClass && this.element.addClass(this.options.activeClass), n && this._trigger("activate", t, this.ui(n));
        },
        _deactivate: function(t) {
            var n = e.ui.ddmanager.current;
            this.options.activeClass && this.element.removeClass(this.options.activeClass), n && this._trigger("deactivate", t, this.ui(n));
        },
        _over: function(t) {
            var n = e.ui.ddmanager.current;
            n && (n.currentItem || n.element)[0] !== this.element[0] && this.accept.call(this.element[0], n.currentItem || n.element) && (this.options.hoverClass && this.element.addClass(this.options.hoverClass), this._trigger("over", t, this.ui(n)));
        },
        _out: function(t) {
            var n = e.ui.ddmanager.current;
            n && (n.currentItem || n.element)[0] !== this.element[0] && this.accept.call(this.element[0], n.currentItem || n.element) && (this.options.hoverClass && this.element.removeClass(this.options.hoverClass), this._trigger("out", t, this.ui(n)));
        },
        _drop: function(t, n) {
            var r = n || e.ui.ddmanager.current, i = !1;
            return r && (r.currentItem || r.element)[0] !== this.element[0] ? (this.element.find(":data(ui-droppable)").not(".ui-draggable-dragging").each(function() {
                var t = e.data(this, "ui-droppable");
                return t.options.greedy && !t.options.disabled && t.options.scope === r.options.scope && t.accept.call(t.element[0], r.currentItem || r.element) && e.ui.intersect(r, e.extend(t, {
                    offset: t.element.offset()
                }), t.options.tolerance) ? (i = !0, !1) : undefined;
            }), i ? !1 : this.accept.call(this.element[0], r.currentItem || r.element) ? (this.options.activeClass && this.element.removeClass(this.options.activeClass), this.options.hoverClass && this.element.removeClass(this.options.hoverClass), this._trigger("drop", t, this.ui(r)), this.element) : !1) : !1;
        },
        ui: function(e) {
            return {
                draggable: e.currentItem || e.element,
                helper: e.helper,
                position: e.position,
                offset: e.positionAbs
            };
        }
    }), e.ui.intersect = function(e, n, r) {
        if (!n.offset) return !1;
        var i, s, o = (e.positionAbs || e.position.absolute).left, u = o + e.helperProportions.width, a = (e.positionAbs || e.position.absolute).top, f = a + e.helperProportions.height, l = n.offset.left, c = l + n.proportions.width, h = n.offset.top, p = h + n.proportions.height;
        switch (r) {
          case "fit":
            return o >= l && c >= u && a >= h && p >= f;
          case "intersect":
            return o + e.helperProportions.width / 2 > l && c > u - e.helperProportions.width / 2 && a + e.helperProportions.height / 2 > h && p > f - e.helperProportions.height / 2;
          case "pointer":
            return i = (e.positionAbs || e.position.absolute).left + (e.clickOffset || e.offset.click).left, s = (e.positionAbs || e.position.absolute).top + (e.clickOffset || e.offset.click).top, t(s, h, n.proportions.height) && t(i, l, n.proportions.width);
          case "touch":
            return (a >= h && p >= a || f >= h && p >= f || h > a && f > p) && (o >= l && c >= o || u >= l && c >= u || l > o && u > c);
          default:
            return !1;
        }
    }, e.ui.ddmanager = {
        current: null,
        droppables: {
            "default": []
        },
        prepareOffsets: function(t, n) {
            var r, i, s = e.ui.ddmanager.droppables[t.options.scope] || [], o = n ? n.type : null, u = (t.currentItem || t.element).find(":data(ui-droppable)").addBack();
            e : for (r = 0; s.length > r; r++) if (!(s[r].options.disabled || t && !s[r].accept.call(s[r].element[0], t.currentItem || t.element))) {
                for (i = 0; u.length > i; i++) if (u[i] === s[r].element[0]) {
                    s[r].proportions.height = 0;
                    continue e;
                }
                s[r].visible = "none" !== s[r].element.css("display"), s[r].visible && ("mousedown" === o && s[r]._activate.call(s[r], n), s[r].offset = s[r].element.offset(), s[r].proportions = {
                    width: s[r].element[0].offsetWidth,
                    height: s[r].element[0].offsetHeight
                });
            }
        },
        drop: function(t, n) {
            var r = !1;
            return e.each((e.ui.ddmanager.droppables[t.options.scope] || []).slice(), function() {
                this.options && (!this.options.disabled && this.visible && e.ui.intersect(t, this, this.options.tolerance) && (r = this._drop.call(this, n) || r), !this.options.disabled && this.visible && this.accept.call(this.element[0], t.currentItem || t.element) && (this.isout = !0, this.isover = !1, this._deactivate.call(this, n)));
            }), r;
        },
        dragStart: function(t, n) {
            t.element.parentsUntil("body").bind("scroll.droppable", function() {
                t.options.refreshPositions || e.ui.ddmanager.prepareOffsets(t, n);
            });
        },
        drag: function(t, n) {
            t.options.refreshPositions && e.ui.ddmanager.prepareOffsets(t, n), e.each(e.ui.ddmanager.droppables[t.options.scope] || [], function() {
                if (!this.options.disabled && !this.greedyChild && this.visible) {
                    var r, s, o, u = e.ui.intersect(t, this, this.options.tolerance), a = !u && this.isover ? "isout" : u && !this.isover ? "isover" : null;
                    a && (this.options.greedy && (s = this.options.scope, o = this.element.parents(":data(ui-droppable)").filter(function() {
                        return e.data(this, "ui-droppable").options.scope === s;
                    }), o.length && (r = e.data(o[0], "ui-droppable"), r.greedyChild = "isover" === a)), r && "isover" === a && (r.isover = !1, r.isout = !0, r._out.call(r, n)), this[a] = !0, this["isout" === a ? "isover" : "isout"] = !1, this["isover" === a ? "_over" : "_out"].call(this, n), r && "isout" === a && (r.isout = !1, r.isover = !0, r._over.call(r, n)));
                }
            });
        },
        dragStop: function(t, n) {
            t.element.parentsUntil("body").unbind("scroll.droppable"), t.options.refreshPositions || e.ui.ddmanager.prepareOffsets(t, n);
        }
    };
})(jQuery);

(function(e) {
    function t(e) {
        return parseInt(e, 10) || 0;
    }
    function n(e) {
        return !isNaN(parseInt(e, 10));
    }
    e.widget("ui.resizable", e.ui.mouse, {
        version: "1.10.3",
        widgetEventPrefix: "resize",
        options: {
            alsoResize: !1,
            animate: !1,
            animateDuration: "slow",
            animateEasing: "swing",
            aspectRatio: !1,
            autoHide: !1,
            containment: !1,
            ghost: !1,
            grid: !1,
            handles: "e,s,se",
            helper: !1,
            maxHeight: null,
            maxWidth: null,
            minHeight: 10,
            minWidth: 10,
            zIndex: 90,
            resize: null,
            start: null,
            stop: null
        },
        _create: function() {
            var t, n, r, i, s, o = this, u = this.options;
            if (this.element.addClass("ui-resizable"), e.extend(this, {
                _aspectRatio: !!u.aspectRatio,
                aspectRatio: u.aspectRatio,
                originalElement: this.element,
                _proportionallyResizeElements: [],
                _helper: u.helper || u.ghost || u.animate ? u.helper || "ui-resizable-helper" : null
            }), this.element[0].nodeName.match(/canvas|textarea|input|select|button|img/i) && (this.element.wrap(e("<div class='ui-wrapper' style='overflow: hidden;'></div>").css({
                position: this.element.css("position"),
                width: this.element.outerWidth(),
                height: this.element.outerHeight(),
                top: this.element.css("top"),
                left: this.element.css("left")
            })), this.element = this.element.parent().data("ui-resizable", this.element.data("ui-resizable")), this.elementIsWrapper = !0, this.element.css({
                marginLeft: this.originalElement.css("marginLeft"),
                marginTop: this.originalElement.css("marginTop"),
                marginRight: this.originalElement.css("marginRight"),
                marginBottom: this.originalElement.css("marginBottom")
            }), this.originalElement.css({
                marginLeft: 0,
                marginTop: 0,
                marginRight: 0,
                marginBottom: 0
            }), this.originalResizeStyle = this.originalElement.css("resize"), this.originalElement.css("resize", "none"), this._proportionallyResizeElements.push(this.originalElement.css({
                position: "static",
                zoom: 1,
                display: "block"
            })), this.originalElement.css({
                margin: this.originalElement.css("margin")
            }), this._proportionallyResize()), this.handles = u.handles || (e(".ui-resizable-handle", this.element).length ? {
                n: ".ui-resizable-n",
                e: ".ui-resizable-e",
                s: ".ui-resizable-s",
                w: ".ui-resizable-w",
                se: ".ui-resizable-se",
                sw: ".ui-resizable-sw",
                ne: ".ui-resizable-ne",
                nw: ".ui-resizable-nw"
            } : "e,s,se"), this.handles.constructor === String) for ("all" === this.handles && (this.handles = "n,e,s,w,se,sw,ne,nw"), t = this.handles.split(","), this.handles = {}, n = 0; t.length > n; n++) r = e.trim(t[n]), s = "ui-resizable-" + r, i = e("<div class='ui-resizable-handle " + s + "'></div>"), i.css({
                zIndex: u.zIndex
            }), "se" === r && i.addClass("ui-icon ui-icon-gripsmall-diagonal-se"), this.handles[r] = ".ui-resizable-" + r, this.element.append(i);
            this._renderAxis = function(t) {
                var n, r, i, s;
                t = t || this.element;
                for (n in this.handles) this.handles[n].constructor === String && (this.handles[n] = e(this.handles[n], this.element).show()), this.elementIsWrapper && this.originalElement[0].nodeName.match(/textarea|input|select|button/i) && (r = e(this.handles[n], this.element), s = /sw|ne|nw|se|n|s/.test(n) ? r.outerHeight() : r.outerWidth(), i = [ "padding", /ne|nw|n/.test(n) ? "Top" : /se|sw|s/.test(n) ? "Bottom" : /^e$/.test(n) ? "Right" : "Left" ].join(""), t.css(i, s), this._proportionallyResize()), e(this.handles[n]).length;
            }, this._renderAxis(this.element), this._handles = e(".ui-resizable-handle", this.element).disableSelection(), this._handles.mouseover(function() {
                o.resizing || (this.className && (i = this.className.match(/ui-resizable-(se|sw|ne|nw|n|e|s|w)/i)), o.axis = i && i[1] ? i[1] : "se");
            }), u.autoHide && (this._handles.hide(), e(this.element).addClass("ui-resizable-autohide").mouseenter(function() {
                u.disabled || (e(this).removeClass("ui-resizable-autohide"), o._handles.show());
            }).mouseleave(function() {
                u.disabled || o.resizing || (e(this).addClass("ui-resizable-autohide"), o._handles.hide());
            })), this._mouseInit();
        },
        _destroy: function() {
            this._mouseDestroy();
            var t, n = function(t) {
                e(t).removeClass("ui-resizable ui-resizable-disabled ui-resizable-resizing").removeData("resizable").removeData("ui-resizable").unbind(".resizable").find(".ui-resizable-handle").remove();
            };
            return this.elementIsWrapper && (n(this.element), t = this.element, this.originalElement.css({
                position: t.css("position"),
                width: t.outerWidth(),
                height: t.outerHeight(),
                top: t.css("top"),
                left: t.css("left")
            }).insertAfter(t), t.remove()), this.originalElement.css("resize", this.originalResizeStyle), n(this.originalElement), this;
        },
        _mouseCapture: function(t) {
            var n, r, i = !1;
            for (n in this.handles) r = e(this.handles[n])[0], (r === t.target || e.contains(r, t.target)) && (i = !0);
            return !this.options.disabled && i;
        },
        _mouseStart: function(n) {
            var r, i, s, o = this.options, u = this.element.position(), a = this.element;
            return this.resizing = !0, /absolute/.test(a.css("position")) ? a.css({
                position: "absolute",
                top: a.css("top"),
                left: a.css("left")
            }) : a.is(".ui-draggable") && a.css({
                position: "absolute",
                top: u.top,
                left: u.left
            }), this._renderProxy(), r = t(this.helper.css("left")), i = t(this.helper.css("top")), o.containment && (r += e(o.containment).scrollLeft() || 0, i += e(o.containment).scrollTop() || 0), this.offset = this.helper.offset(), this.position = {
                left: r,
                top: i
            }, this.size = this._helper ? {
                width: a.outerWidth(),
                height: a.outerHeight()
            } : {
                width: a.width(),
                height: a.height()
            }, this.originalSize = this._helper ? {
                width: a.outerWidth(),
                height: a.outerHeight()
            } : {
                width: a.width(),
                height: a.height()
            }, this.originalPosition = {
                left: r,
                top: i
            }, this.sizeDiff = {
                width: a.outerWidth() - a.width(),
                height: a.outerHeight() - a.height()
            }, this.originalMousePosition = {
                left: n.pageX,
                top: n.pageY
            }, this.aspectRatio = "number" == typeof o.aspectRatio ? o.aspectRatio : this.originalSize.width / this.originalSize.height || 1, s = e(".ui-resizable-" + this.axis).css("cursor"), e("body").css("cursor", "auto" === s ? this.axis + "-resize" : s), a.addClass("ui-resizable-resizing"), this._propagate("start", n), !0;
        },
        _mouseDrag: function(t) {
            var n, r = this.helper, i = {}, s = this.originalMousePosition, o = this.axis, u = this.position.top, a = this.position.left, f = this.size.width, l = this.size.height, c = t.pageX - s.left || 0, h = t.pageY - s.top || 0, p = this._change[o];
            return p ? (n = p.apply(this, [ t, c, h ]), this._updateVirtualBoundaries(t.shiftKey), (this._aspectRatio || t.shiftKey) && (n = this._updateRatio(n, t)), n = this._respectSize(n, t), this._updateCache(n), this._propagate("resize", t), this.position.top !== u && (i.top = this.position.top + "px"), this.position.left !== a && (i.left = this.position.left + "px"), this.size.width !== f && (i.width = this.size.width + "px"), this.size.height !== l && (i.height = this.size.height + "px"), r.css(i), !this._helper && this._proportionallyResizeElements.length && this._proportionallyResize(), e.isEmptyObject(i) || this._trigger("resize", t, this.ui()), !1) : !1;
        },
        _mouseStop: function(t) {
            this.resizing = !1;
            var n, r, i, s, o, u, a, f = this.options, l = this;
            return this._helper && (n = this._proportionallyResizeElements, r = n.length && /textarea/i.test(n[0].nodeName), i = r && e.ui.hasScroll(n[0], "left") ? 0 : l.sizeDiff.height, s = r ? 0 : l.sizeDiff.width, o = {
                width: l.helper.width() - s,
                height: l.helper.height() - i
            }, u = parseInt(l.element.css("left"), 10) + (l.position.left - l.originalPosition.left) || null, a = parseInt(l.element.css("top"), 10) + (l.position.top - l.originalPosition.top) || null, f.animate || this.element.css(e.extend(o, {
                top: a,
                left: u
            })), l.helper.height(l.size.height), l.helper.width(l.size.width), this._helper && !f.animate && this._proportionallyResize()), e("body").css("cursor", "auto"), this.element.removeClass("ui-resizable-resizing"), this._propagate("stop", t), this._helper && this.helper.remove(), !1;
        },
        _updateVirtualBoundaries: function(e) {
            var t, r, s, o, u, a = this.options;
            u = {
                minWidth: n(a.minWidth) ? a.minWidth : 0,
                maxWidth: n(a.maxWidth) ? a.maxWidth : 1 / 0,
                minHeight: n(a.minHeight) ? a.minHeight : 0,
                maxHeight: n(a.maxHeight) ? a.maxHeight : 1 / 0
            }, (this._aspectRatio || e) && (t = u.minHeight * this.aspectRatio, s = u.minWidth / this.aspectRatio, r = u.maxHeight * this.aspectRatio, o = u.maxWidth / this.aspectRatio, t > u.minWidth && (u.minWidth = t), s > u.minHeight && (u.minHeight = s), u.maxWidth > r && (u.maxWidth = r), u.maxHeight > o && (u.maxHeight = o)), this._vBoundaries = u;
        },
        _updateCache: function(e) {
            this.offset = this.helper.offset(), n(e.left) && (this.position.left = e.left), n(e.top) && (this.position.top = e.top), n(e.height) && (this.size.height = e.height), n(e.width) && (this.size.width = e.width);
        },
        _updateRatio: function(e) {
            var t = this.position, r = this.size, s = this.axis;
            return n(e.height) ? e.width = e.height * this.aspectRatio : n(e.width) && (e.height = e.width / this.aspectRatio), "sw" === s && (e.left = t.left + (r.width - e.width), e.top = null), "nw" === s && (e.top = t.top + (r.height - e.height), e.left = t.left + (r.width - e.width)), e;
        },
        _respectSize: function(e) {
            var t = this._vBoundaries, r = this.axis, s = n(e.width) && t.maxWidth && t.maxWidth < e.width, o = n(e.height) && t.maxHeight && t.maxHeight < e.height, u = n(e.width) && t.minWidth && t.minWidth > e.width, a = n(e.height) && t.minHeight && t.minHeight > e.height, f = this.originalPosition.left + this.originalSize.width, l = this.position.top + this.size.height, c = /sw|nw|w/.test(r), h = /nw|ne|n/.test(r);
            return u && (e.width = t.minWidth), a && (e.height = t.minHeight), s && (e.width = t.maxWidth), o && (e.height = t.maxHeight), u && c && (e.left = f - t.minWidth), s && c && (e.left = f - t.maxWidth), a && h && (e.top = l - t.minHeight), o && h && (e.top = l - t.maxHeight), e.width || e.height || e.left || !e.top ? e.width || e.height || e.top || !e.left || (e.left = null) : e.top = null, e;
        },
        _proportionallyResize: function() {
            if (this._proportionallyResizeElements.length) {
                var e, t, n, r, i, s = this.helper || this.element;
                for (e = 0; this._proportionallyResizeElements.length > e; e++) {
                    if (i = this._proportionallyResizeElements[e], !this.borderDif) for (this.borderDif = [], n = [ i.css("borderTopWidth"), i.css("borderRightWidth"), i.css("borderBottomWidth"), i.css("borderLeftWidth") ], r = [ i.css("paddingTop"), i.css("paddingRight"), i.css("paddingBottom"), i.css("paddingLeft") ], t = 0; n.length > t; t++) this.borderDif[t] = (parseInt(n[t], 10) || 0) + (parseInt(r[t], 10) || 0);
                    i.css({
                        height: s.height() - this.borderDif[0] - this.borderDif[2] || 0,
                        width: s.width() - this.borderDif[1] - this.borderDif[3] || 0
                    });
                }
            }
        },
        _renderProxy: function() {
            var t = this.element, n = this.options;
            this.elementOffset = t.offset(), this._helper ? (this.helper = this.helper || e("<div style='overflow:hidden;'></div>"), this.helper.addClass(this._helper).css({
                width: this.element.outerWidth() - 1,
                height: this.element.outerHeight() - 1,
                position: "absolute",
                left: this.elementOffset.left + "px",
                top: this.elementOffset.top + "px",
                zIndex: ++n.zIndex
            }), this.helper.appendTo("body").disableSelection()) : this.helper = this.element;
        },
        _change: {
            e: function(e, t) {
                return {
                    width: this.originalSize.width + t
                };
            },
            w: function(e, t) {
                var n = this.originalSize, r = this.originalPosition;
                return {
                    left: r.left + t,
                    width: n.width - t
                };
            },
            n: function(e, t, n) {
                var r = this.originalSize, i = this.originalPosition;
                return {
                    top: i.top + n,
                    height: r.height - n
                };
            },
            s: function(e, t, n) {
                return {
                    height: this.originalSize.height + n
                };
            },
            se: function(t, n, r) {
                return e.extend(this._change.s.apply(this, arguments), this._change.e.apply(this, [ t, n, r ]));
            },
            sw: function(t, n, r) {
                return e.extend(this._change.s.apply(this, arguments), this._change.w.apply(this, [ t, n, r ]));
            },
            ne: function(t, n, r) {
                return e.extend(this._change.n.apply(this, arguments), this._change.e.apply(this, [ t, n, r ]));
            },
            nw: function(t, n, r) {
                return e.extend(this._change.n.apply(this, arguments), this._change.w.apply(this, [ t, n, r ]));
            }
        },
        _propagate: function(t, n) {
            e.ui.plugin.call(this, t, [ n, this.ui() ]), "resize" !== t && this._trigger(t, n, this.ui());
        },
        plugins: {},
        ui: function() {
            return {
                originalElement: this.originalElement,
                element: this.element,
                helper: this.helper,
                position: this.position,
                size: this.size,
                originalSize: this.originalSize,
                originalPosition: this.originalPosition
            };
        }
    }), e.ui.plugin.add("resizable", "animate", {
        stop: function(t) {
            var n = e(this).data("ui-resizable"), r = n.options, i = n._proportionallyResizeElements, s = i.length && /textarea/i.test(i[0].nodeName), o = s && e.ui.hasScroll(i[0], "left") ? 0 : n.sizeDiff.height, u = s ? 0 : n.sizeDiff.width, a = {
                width: n.size.width - u,
                height: n.size.height - o
            }, f = parseInt(n.element.css("left"), 10) + (n.position.left - n.originalPosition.left) || null, l = parseInt(n.element.css("top"), 10) + (n.position.top - n.originalPosition.top) || null;
            n.element.animate(e.extend(a, l && f ? {
                top: l,
                left: f
            } : {}), {
                duration: r.animateDuration,
                easing: r.animateEasing,
                step: function() {
                    var r = {
                        width: parseInt(n.element.css("width"), 10),
                        height: parseInt(n.element.css("height"), 10),
                        top: parseInt(n.element.css("top"), 10),
                        left: parseInt(n.element.css("left"), 10)
                    };
                    i && i.length && e(i[0]).css({
                        width: r.width,
                        height: r.height
                    }), n._updateCache(r), n._propagate("resize", t);
                }
            });
        }
    }), e.ui.plugin.add("resizable", "containment", {
        start: function() {
            var n, r, i, s, o, u, a, f = e(this).data("ui-resizable"), l = f.options, c = f.element, h = l.containment, p = h instanceof e ? h.get(0) : /parent/.test(h) ? c.parent().get(0) : h;
            p && (f.containerElement = e(p), /document/.test(h) || h === document ? (f.containerOffset = {
                left: 0,
                top: 0
            }, f.containerPosition = {
                left: 0,
                top: 0
            }, f.parentData = {
                element: e(document),
                left: 0,
                top: 0,
                width: e(document).width(),
                height: e(document).height() || document.body.parentNode.scrollHeight
            }) : (n = e(p), r = [], e([ "Top", "Right", "Left", "Bottom" ]).each(function(e, i) {
                r[e] = t(n.css("padding" + i));
            }), f.containerOffset = n.offset(), f.containerPosition = n.position(), f.containerSize = {
                height: n.innerHeight() - r[3],
                width: n.innerWidth() - r[1]
            }, i = f.containerOffset, s = f.containerSize.height, o = f.containerSize.width, u = e.ui.hasScroll(p, "left") ? p.scrollWidth : o, a = e.ui.hasScroll(p) ? p.scrollHeight : s, f.parentData = {
                element: p,
                left: i.left,
                top: i.top,
                width: u,
                height: a
            }));
        },
        resize: function(t) {
            var n, r, i, s, o = e(this).data("ui-resizable"), u = o.options, a = o.containerOffset, f = o.position, l = o._aspectRatio || t.shiftKey, c = {
                top: 0,
                left: 0
            }, h = o.containerElement;
            h[0] !== document && /static/.test(h.css("position")) && (c = a), f.left < (o._helper ? a.left : 0) && (o.size.width = o.size.width + (o._helper ? o.position.left - a.left : o.position.left - c.left), l && (o.size.height = o.size.width / o.aspectRatio), o.position.left = u.helper ? a.left : 0), f.top < (o._helper ? a.top : 0) && (o.size.height = o.size.height + (o._helper ? o.position.top - a.top : o.position.top), l && (o.size.width = o.size.height * o.aspectRatio), o.position.top = o._helper ? a.top : 0), o.offset.left = o.parentData.left + o.position.left, o.offset.top = o.parentData.top + o.position.top, n = Math.abs((o._helper ? o.offset.left - c.left : o.offset.left - c.left) + o.sizeDiff.width), r = Math.abs((o._helper ? o.offset.top - c.top : o.offset.top - a.top) + o.sizeDiff.height), i = o.containerElement.get(0) === o.element.parent().get(0), s = /relative|absolute/.test(o.containerElement.css("position")), i && s && (n -= o.parentData.left), n + o.size.width >= o.parentData.width && (o.size.width = o.parentData.width - n, l && (o.size.height = o.size.width / o.aspectRatio)), r + o.size.height >= o.parentData.height && (o.size.height = o.parentData.height - r, l && (o.size.width = o.size.height * o.aspectRatio));
        },
        stop: function() {
            var t = e(this).data("ui-resizable"), n = t.options, r = t.containerOffset, i = t.containerPosition, s = t.containerElement, o = e(t.helper), u = o.offset(), a = o.outerWidth() - t.sizeDiff.width, f = o.outerHeight() - t.sizeDiff.height;
            t._helper && !n.animate && /relative/.test(s.css("position")) && e(this).css({
                left: u.left - i.left - r.left,
                width: a,
                height: f
            }), t._helper && !n.animate && /static/.test(s.css("position")) && e(this).css({
                left: u.left - i.left - r.left,
                width: a,
                height: f
            });
        }
    }), e.ui.plugin.add("resizable", "alsoResize", {
        start: function() {
            var t = e(this).data("ui-resizable"), n = t.options, r = function(t) {
                e(t).each(function() {
                    var t = e(this);
                    t.data("ui-resizable-alsoresize", {
                        width: parseInt(t.width(), 10),
                        height: parseInt(t.height(), 10),
                        left: parseInt(t.css("left"), 10),
                        top: parseInt(t.css("top"), 10)
                    });
                });
            };
            "object" != typeof n.alsoResize || n.alsoResize.parentNode ? r(n.alsoResize) : n.alsoResize.length ? (n.alsoResize = n.alsoResize[0], r(n.alsoResize)) : e.each(n.alsoResize, function(e) {
                r(e);
            });
        },
        resize: function(t, n) {
            var r = e(this).data("ui-resizable"), i = r.options, s = r.originalSize, o = r.originalPosition, u = {
                height: r.size.height - s.height || 0,
                width: r.size.width - s.width || 0,
                top: r.position.top - o.top || 0,
                left: r.position.left - o.left || 0
            }, a = function(t, r) {
                e(t).each(function() {
                    var t = e(this), i = e(this).data("ui-resizable-alsoresize"), s = {}, o = r && r.length ? r : t.parents(n.originalElement[0]).length ? [ "width", "height" ] : [ "width", "height", "top", "left" ];
                    e.each(o, function(e, t) {
                        var n = (i[t] || 0) + (u[t] || 0);
                        n && n >= 0 && (s[t] = n || null);
                    }), t.css(s);
                });
            };
            "object" != typeof i.alsoResize || i.alsoResize.nodeType ? a(i.alsoResize) : e.each(i.alsoResize, function(e, t) {
                a(e, t);
            });
        },
        stop: function() {
            e(this).removeData("resizable-alsoresize");
        }
    }), e.ui.plugin.add("resizable", "ghost", {
        start: function() {
            var t = e(this).data("ui-resizable"), n = t.options, r = t.size;
            t.ghost = t.originalElement.clone(), t.ghost.css({
                opacity: .25,
                display: "block",
                position: "relative",
                height: r.height,
                width: r.width,
                margin: 0,
                left: 0,
                top: 0
            }).addClass("ui-resizable-ghost").addClass("string" == typeof n.ghost ? n.ghost : ""), t.ghost.appendTo(t.helper);
        },
        resize: function() {
            var t = e(this).data("ui-resizable");
            t.ghost && t.ghost.css({
                position: "relative",
                height: t.size.height,
                width: t.size.width
            });
        },
        stop: function() {
            var t = e(this).data("ui-resizable");
            t.ghost && t.helper && t.helper.get(0).removeChild(t.ghost.get(0));
        }
    }), e.ui.plugin.add("resizable", "grid", {
        resize: function() {
            var t = e(this).data("ui-resizable"), n = t.options, r = t.size, i = t.originalSize, s = t.originalPosition, o = t.axis, u = "number" == typeof n.grid ? [ n.grid, n.grid ] : n.grid, a = u[0] || 1, f = u[1] || 1, l = Math.round((r.width - i.width) / a) * a, c = Math.round((r.height - i.height) / f) * f, h = i.width + l, p = i.height + c, d = n.maxWidth && h > n.maxWidth, v = n.maxHeight && p > n.maxHeight, m = n.minWidth && n.minWidth > h, g = n.minHeight && n.minHeight > p;
            n.grid = u, m && (h += a), g && (p += f), d && (h -= a), v && (p -= f), /^(se|s|e)$/.test(o) ? (t.size.width = h, t.size.height = p) : /^(ne)$/.test(o) ? (t.size.width = h, t.size.height = p, t.position.top = s.top - c) : /^(sw)$/.test(o) ? (t.size.width = h, t.size.height = p, t.position.left = s.left - l) : (t.size.width = h, t.size.height = p, t.position.top = s.top - c, t.position.left = s.left - l);
        }
    });
})(jQuery);

(function(e) {
    e.widget("ui.selectable", e.ui.mouse, {
        version: "1.10.3",
        options: {
            appendTo: "body",
            autoRefresh: !0,
            distance: 0,
            filter: "*",
            tolerance: "touch",
            selected: null,
            selecting: null,
            start: null,
            stop: null,
            unselected: null,
            unselecting: null
        },
        _create: function() {
            var n, r = this;
            this.element.addClass("ui-selectable"), this.dragged = !1, this.refresh = function() {
                n = e(r.options.filter, r.element[0]), n.addClass("ui-selectee"), n.each(function() {
                    var n = e(this), r = n.offset();
                    e.data(this, "selectable-item", {
                        element: this,
                        $element: n,
                        left: r.left,
                        top: r.top,
                        right: r.left + n.outerWidth(),
                        bottom: r.top + n.outerHeight(),
                        startselected: !1,
                        selected: n.hasClass("ui-selected"),
                        selecting: n.hasClass("ui-selecting"),
                        unselecting: n.hasClass("ui-unselecting")
                    });
                });
            }, this.refresh(), this.selectees = n.addClass("ui-selectee"), this._mouseInit(), this.helper = e("<div class='ui-selectable-helper'></div>");
        },
        _destroy: function() {
            this.selectees.removeClass("ui-selectee").removeData("selectable-item"), this.element.removeClass("ui-selectable ui-selectable-disabled"), this._mouseDestroy();
        },
        _mouseStart: function(n) {
            var r = this, i = this.options;
            this.opos = [ n.pageX, n.pageY ], this.options.disabled || (this.selectees = e(i.filter, this.element[0]), this._trigger("start", n), e(i.appendTo).append(this.helper), this.helper.css({
                left: n.pageX,
                top: n.pageY,
                width: 0,
                height: 0
            }), i.autoRefresh && this.refresh(), this.selectees.filter(".ui-selected").each(function() {
                var i = e.data(this, "selectable-item");
                i.startselected = !0, n.metaKey || n.ctrlKey || (i.$element.removeClass("ui-selected"), i.selected = !1, i.$element.addClass("ui-unselecting"), i.unselecting = !0, r._trigger("unselecting", n, {
                    unselecting: i.element
                }));
            }), e(n.target).parents().addBack().each(function() {
                var i, s = e.data(this, "selectable-item");
                return s ? (i = !n.metaKey && !n.ctrlKey || !s.$element.hasClass("ui-selected"), s.$element.removeClass(i ? "ui-unselecting" : "ui-selected").addClass(i ? "ui-selecting" : "ui-unselecting"), s.unselecting = !i, s.selecting = i, s.selected = i, i ? r._trigger("selecting", n, {
                    selecting: s.element
                }) : r._trigger("unselecting", n, {
                    unselecting: s.element
                }), !1) : undefined;
            }));
        },
        _mouseDrag: function(n) {
            if (this.dragged = !0, !this.options.disabled) {
                var r, i = this, s = this.options, o = this.opos[0], u = this.opos[1], a = n.pageX, f = n.pageY;
                return o > a && (r = a, a = o, o = r), u > f && (r = f, f = u, u = r), this.helper.css({
                    left: o,
                    top: u,
                    width: a - o,
                    height: f - u
                }), this.selectees.each(function() {
                    var r = e.data(this, "selectable-item"), c = !1;
                    r && r.element !== i.element[0] && ("touch" === s.tolerance ? c = !(r.left > a || o > r.right || r.top > f || u > r.bottom) : "fit" === s.tolerance && (c = r.left > o && a > r.right && r.top > u && f > r.bottom), c ? (r.selected && (r.$element.removeClass("ui-selected"), r.selected = !1), r.unselecting && (r.$element.removeClass("ui-unselecting"), r.unselecting = !1), r.selecting || (r.$element.addClass("ui-selecting"), r.selecting = !0, i._trigger("selecting", n, {
                        selecting: r.element
                    }))) : (r.selecting && ((n.metaKey || n.ctrlKey) && r.startselected ? (r.$element.removeClass("ui-selecting"), r.selecting = !1, r.$element.addClass("ui-selected"), r.selected = !0) : (r.$element.removeClass("ui-selecting"), r.selecting = !1, r.startselected && (r.$element.addClass("ui-unselecting"), r.unselecting = !0), i._trigger("unselecting", n, {
                        unselecting: r.element
                    }))), r.selected && (n.metaKey || n.ctrlKey || r.startselected || (r.$element.removeClass("ui-selected"), r.selected = !1, r.$element.addClass("ui-unselecting"), r.unselecting = !0, i._trigger("unselecting", n, {
                        unselecting: r.element
                    })))));
                }), !1;
            }
        },
        _mouseStop: function(n) {
            var r = this;
            return this.dragged = !1, e(".ui-unselecting", this.element[0]).each(function() {
                var s = e.data(this, "selectable-item");
                s.$element.removeClass("ui-unselecting"), s.unselecting = !1, s.startselected = !1, r._trigger("unselected", n, {
                    unselected: s.element
                });
            }), e(".ui-selecting", this.element[0]).each(function() {
                var s = e.data(this, "selectable-item");
                s.$element.removeClass("ui-selecting").addClass("ui-selected"), s.selecting = !1, s.selected = !0, s.startselected = !0, r._trigger("selected", n, {
                    selected: s.element
                });
            }), this._trigger("stop", n), this.helper.remove(), !1;
        }
    });
})(jQuery);

(function(e) {
    function t(e, t, n) {
        return e > t && t + n > e;
    }
    function n(e) {
        return /left|right/.test(e.css("float")) || /inline|table-cell/.test(e.css("display"));
    }
    e.widget("ui.sortable", e.ui.mouse, {
        version: "1.10.3",
        widgetEventPrefix: "sort",
        ready: !1,
        options: {
            appendTo: "parent",
            axis: !1,
            connectWith: !1,
            containment: !1,
            cursor: "auto",
            cursorAt: !1,
            dropOnEmpty: !0,
            forcePlaceholderSize: !1,
            forceHelperSize: !1,
            grid: !1,
            handle: !1,
            helper: "original",
            items: "> *",
            opacity: !1,
            placeholder: !1,
            revert: !1,
            scroll: !0,
            scrollSensitivity: 20,
            scrollSpeed: 20,
            scope: "default",
            tolerance: "intersect",
            zIndex: 1e3,
            activate: null,
            beforeStop: null,
            change: null,
            deactivate: null,
            out: null,
            over: null,
            receive: null,
            remove: null,
            sort: null,
            start: null,
            stop: null,
            update: null
        },
        _create: function() {
            var e = this.options;
            this.containerCache = {}, this.element.addClass("ui-sortable"), this.refresh(), this.floating = this.items.length ? "x" === e.axis || n(this.items[0].item) : !1, this.offset = this.element.offset(), this._mouseInit(), this.ready = !0;
        },
        _destroy: function() {
            this.element.removeClass("ui-sortable ui-sortable-disabled"), this._mouseDestroy();
            for (var e = this.items.length - 1; e >= 0; e--) this.items[e].item.removeData(this.widgetName + "-item");
            return this;
        },
        _setOption: function(t, n) {
            "disabled" === t ? (this.options[t] = n, this.widget().toggleClass("ui-sortable-disabled", !!n)) : e.Widget.prototype._setOption.apply(this, arguments);
        },
        _mouseCapture: function(t, n) {
            var r = null, i = !1, s = this;
            return this.reverting ? !1 : this.options.disabled || "static" === this.options.type ? !1 : (this._refreshItems(t), e(t.target).parents().each(function() {
                return e.data(this, s.widgetName + "-item") === s ? (r = e(this), !1) : undefined;
            }), e.data(t.target, s.widgetName + "-item") === s && (r = e(t.target)), r ? !this.options.handle || n || (e(this.options.handle, r).find("*").addBack().each(function() {
                this === t.target && (i = !0);
            }), i) ? (this.currentItem = r, this._removeCurrentsFromItems(), !0) : !1 : !1);
        },
        _mouseStart: function(t, n, r) {
            var i, s, o = this.options;
            if (this.currentContainer = this, this.refreshPositions(), this.helper = this._createHelper(t), this._cacheHelperProportions(), this._cacheMargins(), this.scrollParent = this.helper.scrollParent(), this.offset = this.currentItem.offset(), this.offset = {
                top: this.offset.top - this.margins.top,
                left: this.offset.left - this.margins.left
            }, e.extend(this.offset, {
                click: {
                    left: t.pageX - this.offset.left,
                    top: t.pageY - this.offset.top
                },
                parent: this._getParentOffset(),
                relative: this._getRelativeOffset()
            }), this.helper.css("position", "absolute"), this.cssPosition = this.helper.css("position"), this.originalPosition = this._generatePosition(t), this.originalPageX = t.pageX, this.originalPageY = t.pageY, o.cursorAt && this._adjustOffsetFromHelper(o.cursorAt), this.domPosition = {
                prev: this.currentItem.prev()[0],
                parent: this.currentItem.parent()[0]
            }, this.helper[0] !== this.currentItem[0] && this.currentItem.hide(), this._createPlaceholder(), o.containment && this._setContainment(), o.cursor && "auto" !== o.cursor && (s = this.document.find("body"), this.storedCursor = s.css("cursor"), s.css("cursor", o.cursor), this.storedStylesheet = e("<style>*{ cursor: " + o.cursor + " !important; }</style>").appendTo(s)), o.opacity && (this.helper.css("opacity") && (this._storedOpacity = this.helper.css("opacity")), this.helper.css("opacity", o.opacity)), o.zIndex && (this.helper.css("zIndex") && (this._storedZIndex = this.helper.css("zIndex")), this.helper.css("zIndex", o.zIndex)), this.scrollParent[0] !== document && "HTML" !== this.scrollParent[0].tagName && (this.overflowOffset = this.scrollParent.offset()), this._trigger("start", t, this._uiHash()), this._preserveHelperProportions || this._cacheHelperProportions(), !r) for (i = this.containers.length - 1; i >= 0; i--) this.containers[i]._trigger("activate", t, this._uiHash(this));
            return e.ui.ddmanager && (e.ui.ddmanager.current = this), e.ui.ddmanager && !o.dropBehaviour && e.ui.ddmanager.prepareOffsets(this, t), this.dragging = !0, this.helper.addClass("ui-sortable-helper"), this._mouseDrag(t), !0;
        },
        _mouseDrag: function(t) {
            var n, r, i, s, o = this.options, u = !1;
            for (this.position = this._generatePosition(t), this.positionAbs = this._convertPositionTo("absolute"), this.lastPositionAbs || (this.lastPositionAbs = this.positionAbs), this.options.scroll && (this.scrollParent[0] !== document && "HTML" !== this.scrollParent[0].tagName ? (this.overflowOffset.top + this.scrollParent[0].offsetHeight - t.pageY < o.scrollSensitivity ? this.scrollParent[0].scrollTop = u = this.scrollParent[0].scrollTop + o.scrollSpeed : t.pageY - this.overflowOffset.top < o.scrollSensitivity && (this.scrollParent[0].scrollTop = u = this.scrollParent[0].scrollTop - o.scrollSpeed), this.overflowOffset.left + this.scrollParent[0].offsetWidth - t.pageX < o.scrollSensitivity ? this.scrollParent[0].scrollLeft = u = this.scrollParent[0].scrollLeft + o.scrollSpeed : t.pageX - this.overflowOffset.left < o.scrollSensitivity && (this.scrollParent[0].scrollLeft = u = this.scrollParent[0].scrollLeft - o.scrollSpeed)) : (t.pageY - e(document).scrollTop() < o.scrollSensitivity ? u = e(document).scrollTop(e(document).scrollTop() - o.scrollSpeed) : e(window).height() - (t.pageY - e(document).scrollTop()) < o.scrollSensitivity && (u = e(document).scrollTop(e(document).scrollTop() + o.scrollSpeed)), t.pageX - e(document).scrollLeft() < o.scrollSensitivity ? u = e(document).scrollLeft(e(document).scrollLeft() - o.scrollSpeed) : e(window).width() - (t.pageX - e(document).scrollLeft()) < o.scrollSensitivity && (u = e(document).scrollLeft(e(document).scrollLeft() + o.scrollSpeed))), u !== !1 && e.ui.ddmanager && !o.dropBehaviour && e.ui.ddmanager.prepareOffsets(this, t)), this.positionAbs = this._convertPositionTo("absolute"), this.options.axis && "y" === this.options.axis || (this.helper[0].style.left = this.position.left + "px"), this.options.axis && "x" === this.options.axis || (this.helper[0].style.top = this.position.top + "px"), n = this.items.length - 1; n >= 0; n--) if (r = this.items[n], i = r.item[0], s = this._intersectsWithPointer(r), s && r.instance === this.currentContainer && i !== this.currentItem[0] && this.placeholder[1 === s ? "next" : "prev"]()[0] !== i && !e.contains(this.placeholder[0], i) && ("semi-dynamic" === this.options.type ? !e.contains(this.element[0], i) : !0)) {
                if (this.direction = 1 === s ? "down" : "up", "pointer" !== this.options.tolerance && !this._intersectsWithSides(r)) break;
                this._rearrange(t, r), this._trigger("change", t, this._uiHash());
                break;
            }
            return this._contactContainers(t), e.ui.ddmanager && e.ui.ddmanager.drag(this, t), this._trigger("sort", t, this._uiHash()), this.lastPositionAbs = this.positionAbs, !1;
        },
        _mouseStop: function(t, n) {
            if (t) {
                if (e.ui.ddmanager && !this.options.dropBehaviour && e.ui.ddmanager.drop(this, t), this.options.revert) {
                    var r = this, i = this.placeholder.offset(), s = this.options.axis, o = {};
                    s && "x" !== s || (o.left = i.left - this.offset.parent.left - this.margins.left + (this.offsetParent[0] === document.body ? 0 : this.offsetParent[0].scrollLeft)), s && "y" !== s || (o.top = i.top - this.offset.parent.top - this.margins.top + (this.offsetParent[0] === document.body ? 0 : this.offsetParent[0].scrollTop)), this.reverting = !0, e(this.helper).animate(o, parseInt(this.options.revert, 10) || 500, function() {
                        r._clear(t);
                    });
                } else this._clear(t, n);
                return !1;
            }
        },
        cancel: function() {
            if (this.dragging) {
                this._mouseUp({
                    target: null
                }), "original" === this.options.helper ? this.currentItem.css(this._storedCSS).removeClass("ui-sortable-helper") : this.currentItem.show();
                for (var t = this.containers.length - 1; t >= 0; t--) this.containers[t]._trigger("deactivate", null, this._uiHash(this)), this.containers[t].containerCache.over && (this.containers[t]._trigger("out", null, this._uiHash(this)), this.containers[t].containerCache.over = 0);
            }
            return this.placeholder && (this.placeholder[0].parentNode && this.placeholder[0].parentNode.removeChild(this.placeholder[0]), "original" !== this.options.helper && this.helper && this.helper[0].parentNode && this.helper.remove(), e.extend(this, {
                helper: null,
                dragging: !1,
                reverting: !1,
                _noFinalSort: null
            }), this.domPosition.prev ? e(this.domPosition.prev).after(this.currentItem) : e(this.domPosition.parent).prepend(this.currentItem)), this;
        },
        serialize: function(t) {
            var n = this._getItemsAsjQuery(t && t.connected), r = [];
            return t = t || {}, e(n).each(function() {
                var n = (e(t.item || this).attr(t.attribute || "id") || "").match(t.expression || /(.+)[\-=_](.+)/);
                n && r.push((t.key || n[1] + "[]") + "=" + (t.key && t.expression ? n[1] : n[2]));
            }), !r.length && t.key && r.push(t.key + "="), r.join("&");
        },
        toArray: function(t) {
            var n = this._getItemsAsjQuery(t && t.connected), r = [];
            return t = t || {}, n.each(function() {
                r.push(e(t.item || this).attr(t.attribute || "id") || "");
            }), r;
        },
        _intersectsWith: function(e) {
            var t = this.positionAbs.left, n = t + this.helperProportions.width, r = this.positionAbs.top, i = r + this.helperProportions.height, s = e.left, o = s + e.width, u = e.top, a = u + e.height, f = this.offset.click.top, l = this.offset.click.left, c = "x" === this.options.axis || r + f > u && a > r + f, h = "y" === this.options.axis || t + l > s && o > t + l, p = c && h;
            return "pointer" === this.options.tolerance || this.options.forcePointerForContainers || "pointer" !== this.options.tolerance && this.helperProportions[this.floating ? "width" : "height"] > e[this.floating ? "width" : "height"] ? p : t + this.helperProportions.width / 2 > s && o > n - this.helperProportions.width / 2 && r + this.helperProportions.height / 2 > u && a > i - this.helperProportions.height / 2;
        },
        _intersectsWithPointer: function(e) {
            var n = "x" === this.options.axis || t(this.positionAbs.top + this.offset.click.top, e.top, e.height), r = "y" === this.options.axis || t(this.positionAbs.left + this.offset.click.left, e.left, e.width), i = n && r, s = this._getDragVerticalDirection(), o = this._getDragHorizontalDirection();
            return i ? this.floating ? o && "right" === o || "down" === s ? 2 : 1 : s && ("down" === s ? 2 : 1) : !1;
        },
        _intersectsWithSides: function(e) {
            var n = t(this.positionAbs.top + this.offset.click.top, e.top + e.height / 2, e.height), r = t(this.positionAbs.left + this.offset.click.left, e.left + e.width / 2, e.width), i = this._getDragVerticalDirection(), s = this._getDragHorizontalDirection();
            return this.floating && s ? "right" === s && r || "left" === s && !r : i && ("down" === i && n || "up" === i && !n);
        },
        _getDragVerticalDirection: function() {
            var e = this.positionAbs.top - this.lastPositionAbs.top;
            return 0 !== e && (e > 0 ? "down" : "up");
        },
        _getDragHorizontalDirection: function() {
            var e = this.positionAbs.left - this.lastPositionAbs.left;
            return 0 !== e && (e > 0 ? "right" : "left");
        },
        refresh: function(e) {
            return this._refreshItems(e), this.refreshPositions(), this;
        },
        _connectWith: function() {
            var e = this.options;
            return e.connectWith.constructor === String ? [ e.connectWith ] : e.connectWith;
        },
        _getItemsAsjQuery: function(t) {
            var n, r, i, s, o = [], u = [], a = this._connectWith();
            if (a && t) for (n = a.length - 1; n >= 0; n--) for (i = e(a[n]), r = i.length - 1; r >= 0; r--) s = e.data(i[r], this.widgetFullName), s && s !== this && !s.options.disabled && u.push([ e.isFunction(s.options.items) ? s.options.items.call(s.element) : e(s.options.items, s.element).not(".ui-sortable-helper").not(".ui-sortable-placeholder"), s ]);
            for (u.push([ e.isFunction(this.options.items) ? this.options.items.call(this.element, null, {
                options: this.options,
                item: this.currentItem
            }) : e(this.options.items, this.element).not(".ui-sortable-helper").not(".ui-sortable-placeholder"), this ]), n = u.length - 1; n >= 0; n--) u[n][0].each(function() {
                o.push(this);
            });
            return e(o);
        },
        _removeCurrentsFromItems: function() {
            var t = this.currentItem.find(":data(" + this.widgetName + "-item)");
            this.items = e.grep(this.items, function(e) {
                for (var n = 0; t.length > n; n++) if (t[n] === e.item[0]) return !1;
                return !0;
            });
        },
        _refreshItems: function(t) {
            this.items = [], this.containers = [ this ];
            var n, r, i, s, o, u, a, f, l = this.items, c = [ [ e.isFunction(this.options.items) ? this.options.items.call(this.element[0], t, {
                item: this.currentItem
            }) : e(this.options.items, this.element), this ] ], h = this._connectWith();
            if (h && this.ready) for (n = h.length - 1; n >= 0; n--) for (i = e(h[n]), r = i.length - 1; r >= 0; r--) s = e.data(i[r], this.widgetFullName), s && s !== this && !s.options.disabled && (c.push([ e.isFunction(s.options.items) ? s.options.items.call(s.element[0], t, {
                item: this.currentItem
            }) : e(s.options.items, s.element), s ]), this.containers.push(s));
            for (n = c.length - 1; n >= 0; n--) for (o = c[n][1], u = c[n][0], r = 0, f = u.length; f > r; r++) a = e(u[r]), a.data(this.widgetName + "-item", o), l.push({
                item: a,
                instance: o,
                width: 0,
                height: 0,
                left: 0,
                top: 0
            });
        },
        refreshPositions: function(t) {
            this.offsetParent && this.helper && (this.offset.parent = this._getParentOffset());
            var n, r, i, s;
            for (n = this.items.length - 1; n >= 0; n--) r = this.items[n], r.instance !== this.currentContainer && this.currentContainer && r.item[0] !== this.currentItem[0] || (i = this.options.toleranceElement ? e(this.options.toleranceElement, r.item) : r.item, t || (r.width = i.outerWidth(), r.height = i.outerHeight()), s = i.offset(), r.left = s.left, r.top = s.top);
            if (this.options.custom && this.options.custom.refreshContainers) this.options.custom.refreshContainers.call(this); else for (n = this.containers.length - 1; n >= 0; n--) s = this.containers[n].element.offset(), this.containers[n].containerCache.left = s.left, this.containers[n].containerCache.top = s.top, this.containers[n].containerCache.width = this.containers[n].element.outerWidth(), this.containers[n].containerCache.height = this.containers[n].element.outerHeight();
            return this;
        },
        _createPlaceholder: function(t) {
            t = t || this;
            var n, r = t.options;
            r.placeholder && r.placeholder.constructor !== String || (n = r.placeholder, r.placeholder = {
                element: function() {
                    var r = t.currentItem[0].nodeName.toLowerCase(), s = e("<" + r + ">", t.document[0]).addClass(n || t.currentItem[0].className + " ui-sortable-placeholder").removeClass("ui-sortable-helper");
                    return "tr" === r ? t.currentItem.children().each(function() {
                        e("<td>&#160;</td>", t.document[0]).attr("colspan", e(this).attr("colspan") || 1).appendTo(s);
                    }) : "img" === r && s.attr("src", t.currentItem.attr("src")), n || s.css("visibility", "hidden"), s;
                },
                update: function(e, o) {
                    (!n || r.forcePlaceholderSize) && (o.height() || o.height(t.currentItem.innerHeight() - parseInt(t.currentItem.css("paddingTop") || 0, 10) - parseInt(t.currentItem.css("paddingBottom") || 0, 10)), o.width() || o.width(t.currentItem.innerWidth() - parseInt(t.currentItem.css("paddingLeft") || 0, 10) - parseInt(t.currentItem.css("paddingRight") || 0, 10)));
                }
            }), t.placeholder = e(r.placeholder.element.call(t.element, t.currentItem)), t.currentItem.after(t.placeholder), r.placeholder.update(t, t.placeholder);
        },
        _contactContainers: function(r) {
            var s, o, u, a, f, l, c, h, p, d, v = null, m = null;
            for (s = this.containers.length - 1; s >= 0; s--) if (!e.contains(this.currentItem[0], this.containers[s].element[0])) if (this._intersectsWith(this.containers[s].containerCache)) {
                if (v && e.contains(this.containers[s].element[0], v.element[0])) continue;
                v = this.containers[s], m = s;
            } else this.containers[s].containerCache.over && (this.containers[s]._trigger("out", r, this._uiHash(this)), this.containers[s].containerCache.over = 0);
            if (v) if (1 === this.containers.length) this.containers[m].containerCache.over || (this.containers[m]._trigger("over", r, this._uiHash(this)), this.containers[m].containerCache.over = 1); else {
                for (u = 1e4, a = null, d = v.floating || n(this.currentItem), f = d ? "left" : "top", l = d ? "width" : "height", c = this.positionAbs[f] + this.offset.click[f], o = this.items.length - 1; o >= 0; o--) e.contains(this.containers[m].element[0], this.items[o].item[0]) && this.items[o].item[0] !== this.currentItem[0] && (!d || t(this.positionAbs.top + this.offset.click.top, this.items[o].top, this.items[o].height)) && (h = this.items[o].item.offset()[f], p = !1, Math.abs(h - c) > Math.abs(h + this.items[o][l] - c) && (p = !0, h += this.items[o][l]), u > Math.abs(h - c) && (u = Math.abs(h - c), a = this.items[o], this.direction = p ? "up" : "down"));
                if (!a && !this.options.dropOnEmpty) return;
                if (this.currentContainer === this.containers[m]) return;
                a ? this._rearrange(r, a, null, !0) : this._rearrange(r, null, this.containers[m].element, !0), this._trigger("change", r, this._uiHash()), this.containers[m]._trigger("change", r, this._uiHash(this)), this.currentContainer = this.containers[m], this.options.placeholder.update(this.currentContainer, this.placeholder), this.containers[m]._trigger("over", r, this._uiHash(this)), this.containers[m].containerCache.over = 1;
            }
        },
        _createHelper: function(t) {
            var n = this.options, r = e.isFunction(n.helper) ? e(n.helper.apply(this.element[0], [ t, this.currentItem ])) : "clone" === n.helper ? this.currentItem.clone() : this.currentItem;
            return r.parents("body").length || e("parent" !== n.appendTo ? n.appendTo : this.currentItem[0].parentNode)[0].appendChild(r[0]), r[0] === this.currentItem[0] && (this._storedCSS = {
                width: this.currentItem[0].style.width,
                height: this.currentItem[0].style.height,
                position: this.currentItem.css("position"),
                top: this.currentItem.css("top"),
                left: this.currentItem.css("left")
            }), (!r[0].style.width || n.forceHelperSize) && r.width(this.currentItem.width()), (!r[0].style.height || n.forceHelperSize) && r.height(this.currentItem.height()), r;
        },
        _adjustOffsetFromHelper: function(t) {
            "string" == typeof t && (t = t.split(" ")), e.isArray(t) && (t = {
                left: +t[0],
                top: +t[1] || 0
            }), "left" in t && (this.offset.click.left = t.left + this.margins.left), "right" in t && (this.offset.click.left = this.helperProportions.width - t.right + this.margins.left), "top" in t && (this.offset.click.top = t.top + this.margins.top), "bottom" in t && (this.offset.click.top = this.helperProportions.height - t.bottom + this.margins.top);
        },
        _getParentOffset: function() {
            this.offsetParent = this.helper.offsetParent();
            var t = this.offsetParent.offset();
            return "absolute" === this.cssPosition && this.scrollParent[0] !== document && e.contains(this.scrollParent[0], this.offsetParent[0]) && (t.left += this.scrollParent.scrollLeft(), t.top += this.scrollParent.scrollTop()), (this.offsetParent[0] === document.body || this.offsetParent[0].tagName && "html" === this.offsetParent[0].tagName.toLowerCase() && e.ui.ie) && (t = {
                top: 0,
                left: 0
            }), {
                top: t.top + (parseInt(this.offsetParent.css("borderTopWidth"), 10) || 0),
                left: t.left + (parseInt(this.offsetParent.css("borderLeftWidth"), 10) || 0)
            };
        },
        _getRelativeOffset: function() {
            if ("relative" === this.cssPosition) {
                var e = this.currentItem.position();
                return {
                    top: e.top - (parseInt(this.helper.css("top"), 10) || 0) + this.scrollParent.scrollTop(),
                    left: e.left - (parseInt(this.helper.css("left"), 10) || 0) + this.scrollParent.scrollLeft()
                };
            }
            return {
                top: 0,
                left: 0
            };
        },
        _cacheMargins: function() {
            this.margins = {
                left: parseInt(this.currentItem.css("marginLeft"), 10) || 0,
                top: parseInt(this.currentItem.css("marginTop"), 10) || 0
            };
        },
        _cacheHelperProportions: function() {
            this.helperProportions = {
                width: this.helper.outerWidth(),
                height: this.helper.outerHeight()
            };
        },
        _setContainment: function() {
            var t, n, r, i = this.options;
            "parent" === i.containment && (i.containment = this.helper[0].parentNode), ("document" === i.containment || "window" === i.containment) && (this.containment = [ 0 - this.offset.relative.left - this.offset.parent.left, 0 - this.offset.relative.top - this.offset.parent.top, e("document" === i.containment ? document : window).width() - this.helperProportions.width - this.margins.left, (e("document" === i.containment ? document : window).height() || document.body.parentNode.scrollHeight) - this.helperProportions.height - this.margins.top ]), /^(document|window|parent)$/.test(i.containment) || (t = e(i.containment)[0], n = e(i.containment).offset(), r = "hidden" !== e(t).css("overflow"), this.containment = [ n.left + (parseInt(e(t).css("borderLeftWidth"), 10) || 0) + (parseInt(e(t).css("paddingLeft"), 10) || 0) - this.margins.left, n.top + (parseInt(e(t).css("borderTopWidth"), 10) || 0) + (parseInt(e(t).css("paddingTop"), 10) || 0) - this.margins.top, n.left + (r ? Math.max(t.scrollWidth, t.offsetWidth) : t.offsetWidth) - (parseInt(e(t).css("borderLeftWidth"), 10) || 0) - (parseInt(e(t).css("paddingRight"), 10) || 0) - this.helperProportions.width - this.margins.left, n.top + (r ? Math.max(t.scrollHeight, t.offsetHeight) : t.offsetHeight) - (parseInt(e(t).css("borderTopWidth"), 10) || 0) - (parseInt(e(t).css("paddingBottom"), 10) || 0) - this.helperProportions.height - this.margins.top ]);
        },
        _convertPositionTo: function(t, n) {
            n || (n = this.position);
            var r = "absolute" === t ? 1 : -1, i = "absolute" !== this.cssPosition || this.scrollParent[0] !== document && e.contains(this.scrollParent[0], this.offsetParent[0]) ? this.scrollParent : this.offsetParent, s = /(html|body)/i.test(i[0].tagName);
            return {
                top: n.top + this.offset.relative.top * r + this.offset.parent.top * r - ("fixed" === this.cssPosition ? -this.scrollParent.scrollTop() : s ? 0 : i.scrollTop()) * r,
                left: n.left + this.offset.relative.left * r + this.offset.parent.left * r - ("fixed" === this.cssPosition ? -this.scrollParent.scrollLeft() : s ? 0 : i.scrollLeft()) * r
            };
        },
        _generatePosition: function(t) {
            var n, r, i = this.options, s = t.pageX, o = t.pageY, u = "absolute" !== this.cssPosition || this.scrollParent[0] !== document && e.contains(this.scrollParent[0], this.offsetParent[0]) ? this.scrollParent : this.offsetParent, a = /(html|body)/i.test(u[0].tagName);
            return "relative" !== this.cssPosition || this.scrollParent[0] !== document && this.scrollParent[0] !== this.offsetParent[0] || (this.offset.relative = this._getRelativeOffset()), this.originalPosition && (this.containment && (t.pageX - this.offset.click.left < this.containment[0] && (s = this.containment[0] + this.offset.click.left), t.pageY - this.offset.click.top < this.containment[1] && (o = this.containment[1] + this.offset.click.top), t.pageX - this.offset.click.left > this.containment[2] && (s = this.containment[2] + this.offset.click.left), t.pageY - this.offset.click.top > this.containment[3] && (o = this.containment[3] + this.offset.click.top)), i.grid && (n = this.originalPageY + Math.round((o - this.originalPageY) / i.grid[1]) * i.grid[1], o = this.containment ? n - this.offset.click.top >= this.containment[1] && n - this.offset.click.top <= this.containment[3] ? n : n - this.offset.click.top >= this.containment[1] ? n - i.grid[1] : n + i.grid[1] : n, r = this.originalPageX + Math.round((s - this.originalPageX) / i.grid[0]) * i.grid[0], s = this.containment ? r - this.offset.click.left >= this.containment[0] && r - this.offset.click.left <= this.containment[2] ? r : r - this.offset.click.left >= this.containment[0] ? r - i.grid[0] : r + i.grid[0] : r)), {
                top: o - this.offset.click.top - this.offset.relative.top - this.offset.parent.top + ("fixed" === this.cssPosition ? -this.scrollParent.scrollTop() : a ? 0 : u.scrollTop()),
                left: s - this.offset.click.left - this.offset.relative.left - this.offset.parent.left + ("fixed" === this.cssPosition ? -this.scrollParent.scrollLeft() : a ? 0 : u.scrollLeft())
            };
        },
        _rearrange: function(e, t, n, r) {
            n ? n[0].appendChild(this.placeholder[0]) : t.item[0].parentNode.insertBefore(this.placeholder[0], "down" === this.direction ? t.item[0] : t.item[0].nextSibling), this.counter = this.counter ? ++this.counter : 1;
            var i = this.counter;
            this._delay(function() {
                i === this.counter && this.refreshPositions(!r);
            });
        },
        _clear: function(e, t) {
            this.reverting = !1;
            var n, r = [];
            if (!this._noFinalSort && this.currentItem.parent().length && this.placeholder.before(this.currentItem), this._noFinalSort = null, this.helper[0] === this.currentItem[0]) {
                for (n in this._storedCSS) ("auto" === this._storedCSS[n] || "static" === this._storedCSS[n]) && (this._storedCSS[n] = "");
                this.currentItem.css(this._storedCSS).removeClass("ui-sortable-helper");
            } else this.currentItem.show();
            for (this.fromOutside && !t && r.push(function(e) {
                this._trigger("receive", e, this._uiHash(this.fromOutside));
            }), !this.fromOutside && this.domPosition.prev === this.currentItem.prev().not(".ui-sortable-helper")[0] && this.domPosition.parent === this.currentItem.parent()[0] || t || r.push(function(e) {
                this._trigger("update", e, this._uiHash());
            }), this !== this.currentContainer && (t || (r.push(function(e) {
                this._trigger("remove", e, this._uiHash());
            }), r.push(function(e) {
                return function(t) {
                    e._trigger("receive", t, this._uiHash(this));
                };
            }.call(this, this.currentContainer)), r.push(function(e) {
                return function(t) {
                    e._trigger("update", t, this._uiHash(this));
                };
            }.call(this, this.currentContainer)))), n = this.containers.length - 1; n >= 0; n--) t || r.push(function(e) {
                return function(t) {
                    e._trigger("deactivate", t, this._uiHash(this));
                };
            }.call(this, this.containers[n])), this.containers[n].containerCache.over && (r.push(function(e) {
                return function(t) {
                    e._trigger("out", t, this._uiHash(this));
                };
            }.call(this, this.containers[n])), this.containers[n].containerCache.over = 0);
            if (this.storedCursor && (this.document.find("body").css("cursor", this.storedCursor), this.storedStylesheet.remove()), this._storedOpacity && this.helper.css("opacity", this._storedOpacity), this._storedZIndex && this.helper.css("zIndex", "auto" === this._storedZIndex ? "" : this._storedZIndex), this.dragging = !1, this.cancelHelperRemoval) {
                if (!t) {
                    for (this._trigger("beforeStop", e, this._uiHash()), n = 0; r.length > n; n++) r[n].call(this, e);
                    this._trigger("stop", e, this._uiHash());
                }
                return this.fromOutside = !1, !1;
            }
            if (t || this._trigger("beforeStop", e, this._uiHash()), this.placeholder[0].parentNode.removeChild(this.placeholder[0]), this.helper[0] !== this.currentItem[0] && this.helper.remove(), this.helper = null, !t) {
                for (n = 0; r.length > n; n++) r[n].call(this, e);
                this._trigger("stop", e, this._uiHash());
            }
            return this.fromOutside = !1, !0;
        },
        _trigger: function() {
            e.Widget.prototype._trigger.apply(this, arguments) === !1 && this.cancel();
        },
        _uiHash: function(t) {
            var n = t || this;
            return {
                helper: n.helper,
                placeholder: n.placeholder || e([]),
                position: n.position,
                originalPosition: n.originalPosition,
                offset: n.positionAbs,
                item: n.currentItem,
                sender: t ? t.element : null
            };
        }
    });
})(jQuery);

(function(e) {
    var t = 0, n = {}, r = {};
    n.height = n.paddingTop = n.paddingBottom = n.borderTopWidth = n.borderBottomWidth = "hide", r.height = r.paddingTop = r.paddingBottom = r.borderTopWidth = r.borderBottomWidth = "show", e.widget("ui.accordion", {
        version: "1.10.3",
        options: {
            active: 0,
            animate: {},
            collapsible: !1,
            event: "click",
            header: "> li > :first-child,> :not(li):even",
            heightStyle: "auto",
            icons: {
                activeHeader: "ui-icon-triangle-1-s",
                header: "ui-icon-triangle-1-e"
            },
            activate: null,
            beforeActivate: null
        },
        _create: function() {
            var t = this.options;
            this.prevShow = this.prevHide = e(), this.element.addClass("ui-accordion ui-widget ui-helper-reset").attr("role", "tablist"), t.collapsible || t.active !== !1 && null != t.active || (t.active = 0), this._processPanels(), 0 > t.active && (t.active += this.headers.length), this._refresh();
        },
        _getCreateEventData: function() {
            return {
                header: this.active,
                panel: this.active.length ? this.active.next() : e(),
                content: this.active.length ? this.active.next() : e()
            };
        },
        _createIcons: function() {
            var t = this.options.icons;
            t && (e("<span>").addClass("ui-accordion-header-icon ui-icon " + t.header).prependTo(this.headers), this.active.children(".ui-accordion-header-icon").removeClass(t.header).addClass(t.activeHeader), this.headers.addClass("ui-accordion-icons"));
        },
        _destroyIcons: function() {
            this.headers.removeClass("ui-accordion-icons").children(".ui-accordion-header-icon").remove();
        },
        _destroy: function() {
            var e;
            this.element.removeClass("ui-accordion ui-widget ui-helper-reset").removeAttr("role"), this.headers.removeClass("ui-accordion-header ui-accordion-header-active ui-helper-reset ui-state-default ui-corner-all ui-state-active ui-state-disabled ui-corner-top").removeAttr("role").removeAttr("aria-selected").removeAttr("aria-controls").removeAttr("tabIndex").each(function() {
                /^ui-accordion/.test(this.id) && this.removeAttribute("id");
            }), this._destroyIcons(), e = this.headers.next().css("display", "").removeAttr("role").removeAttr("aria-expanded").removeAttr("aria-hidden").removeAttr("aria-labelledby").removeClass("ui-helper-reset ui-widget-content ui-corner-bottom ui-accordion-content ui-accordion-content-active ui-state-disabled").each(function() {
                /^ui-accordion/.test(this.id) && this.removeAttribute("id");
            }), "content" !== this.options.heightStyle && e.css("height", "");
        },
        _setOption: function(e, t) {
            return "active" === e ? (this._activate(t), undefined) : ("event" === e && (this.options.event && this._off(this.headers, this.options.event), this._setupEvents(t)), this._super(e, t), "collapsible" !== e || t || this.options.active !== !1 || this._activate(0), "icons" === e && (this._destroyIcons(), t && this._createIcons()), "disabled" === e && this.headers.add(this.headers.next()).toggleClass("ui-state-disabled", !!t), undefined);
        },
        _keydown: function(t) {
            if (!t.altKey && !t.ctrlKey) {
                var n = e.ui.keyCode, r = this.headers.length, i = this.headers.index(t.target), s = !1;
                switch (t.keyCode) {
                  case n.RIGHT:
                  case n.DOWN:
                    s = this.headers[(i + 1) % r];
                    break;
                  case n.LEFT:
                  case n.UP:
                    s = this.headers[(i - 1 + r) % r];
                    break;
                  case n.SPACE:
                  case n.ENTER:
                    this._eventHandler(t);
                    break;
                  case n.HOME:
                    s = this.headers[0];
                    break;
                  case n.END:
                    s = this.headers[r - 1];
                }
                s && (e(t.target).attr("tabIndex", -1), e(s).attr("tabIndex", 0), s.focus(), t.preventDefault());
            }
        },
        _panelKeyDown: function(t) {
            t.keyCode === e.ui.keyCode.UP && t.ctrlKey && e(t.currentTarget).prev().focus();
        },
        refresh: function() {
            var t = this.options;
            this._processPanels(), t.active === !1 && t.collapsible === !0 || !this.headers.length ? (t.active = !1, this.active = e()) : t.active === !1 ? this._activate(0) : this.active.length && !e.contains(this.element[0], this.active[0]) ? this.headers.length === this.headers.find(".ui-state-disabled").length ? (t.active = !1, this.active = e()) : this._activate(Math.max(0, t.active - 1)) : t.active = this.headers.index(this.active), this._destroyIcons(), this._refresh();
        },
        _processPanels: function() {
            this.headers = this.element.find(this.options.header).addClass("ui-accordion-header ui-helper-reset ui-state-default ui-corner-all"), this.headers.next().addClass("ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom").filter(":not(.ui-accordion-content-active)").hide();
        },
        _refresh: function() {
            var n, r = this.options, i = r.heightStyle, s = this.element.parent(), o = this.accordionId = "ui-accordion-" + (this.element.attr("id") || ++t);
            this.active = this._findActive(r.active).addClass("ui-accordion-header-active ui-state-active ui-corner-top").removeClass("ui-corner-all"), this.active.next().addClass("ui-accordion-content-active").show(), this.headers.attr("role", "tab").each(function(t) {
                var n = e(this), r = n.attr("id"), i = n.next(), s = i.attr("id");
                r || (r = o + "-header-" + t, n.attr("id", r)), s || (s = o + "-panel-" + t, i.attr("id", s)), n.attr("aria-controls", s), i.attr("aria-labelledby", r);
            }).next().attr("role", "tabpanel"), this.headers.not(this.active).attr({
                "aria-selected": "false",
                tabIndex: -1
            }).next().attr({
                "aria-expanded": "false",
                "aria-hidden": "true"
            }).hide(), this.active.length ? this.active.attr({
                "aria-selected": "true",
                tabIndex: 0
            }).next().attr({
                "aria-expanded": "true",
                "aria-hidden": "false"
            }) : this.headers.eq(0).attr("tabIndex", 0), this._createIcons(), this._setupEvents(r.event), "fill" === i ? (n = s.height(), this.element.siblings(":visible").each(function() {
                var t = e(this), r = t.css("position");
                "absolute" !== r && "fixed" !== r && (n -= t.outerHeight(!0));
            }), this.headers.each(function() {
                n -= e(this).outerHeight(!0);
            }), this.headers.next().each(function() {
                e(this).height(Math.max(0, n - e(this).innerHeight() + e(this).height()));
            }).css("overflow", "auto")) : "auto" === i && (n = 0, this.headers.next().each(function() {
                n = Math.max(n, e(this).css("height", "").height());
            }).height(n));
        },
        _activate: function(t) {
            var n = this._findActive(t)[0];
            n !== this.active[0] && (n = n || this.active[0], this._eventHandler({
                target: n,
                currentTarget: n,
                preventDefault: e.noop
            }));
        },
        _findActive: function(t) {
            return "number" == typeof t ? this.headers.eq(t) : e();
        },
        _setupEvents: function(t) {
            var n = {
                keydown: "_keydown"
            };
            t && e.each(t.split(" "), function(e, t) {
                n[t] = "_eventHandler";
            }), this._off(this.headers.add(this.headers.next())), this._on(this.headers, n), this._on(this.headers.next(), {
                keydown: "_panelKeyDown"
            }), this._hoverable(this.headers), this._focusable(this.headers);
        },
        _eventHandler: function(t) {
            var n = this.options, r = this.active, i = e(t.currentTarget), s = i[0] === r[0], o = s && n.collapsible, u = o ? e() : i.next(), a = r.next(), f = {
                oldHeader: r,
                oldPanel: a,
                newHeader: o ? e() : i,
                newPanel: u
            };
            t.preventDefault(), s && !n.collapsible || this._trigger("beforeActivate", t, f) === !1 || (n.active = o ? !1 : this.headers.index(i), this.active = s ? e() : i, this._toggle(f), r.removeClass("ui-accordion-header-active ui-state-active"), n.icons && r.children(".ui-accordion-header-icon").removeClass(n.icons.activeHeader).addClass(n.icons.header), s || (i.removeClass("ui-corner-all").addClass("ui-accordion-header-active ui-state-active ui-corner-top"), n.icons && i.children(".ui-accordion-header-icon").removeClass(n.icons.header).addClass(n.icons.activeHeader), i.next().addClass("ui-accordion-content-active")));
        },
        _toggle: function(t) {
            var n = t.newPanel, r = this.prevShow.length ? this.prevShow : t.oldPanel;
            this.prevShow.add(this.prevHide).stop(!0, !0), this.prevShow = n, this.prevHide = r, this.options.animate ? this._animate(n, r, t) : (r.hide(), n.show(), this._toggleComplete(t)), r.attr({
                "aria-expanded": "false",
                "aria-hidden": "true"
            }), r.prev().attr("aria-selected", "false"), n.length && r.length ? r.prev().attr("tabIndex", -1) : n.length && this.headers.filter(function() {
                return 0 === e(this).attr("tabIndex");
            }).attr("tabIndex", -1), n.attr({
                "aria-expanded": "true",
                "aria-hidden": "false"
            }).prev().attr({
                "aria-selected": "true",
                tabIndex: 0
            });
        },
        _animate: function(e, t, s) {
            var o, u, a, f = this, l = 0, c = e.length && (!t.length || e.index() < t.index()), h = this.options.animate || {}, p = c && h.down || h, d = function() {
                f._toggleComplete(s);
            };
            return "number" == typeof p && (a = p), "string" == typeof p && (u = p), u = u || p.easing || h.easing, a = a || p.duration || h.duration, t.length ? e.length ? (o = e.show().outerHeight(), t.animate(n, {
                duration: a,
                easing: u,
                step: function(e, t) {
                    t.now = Math.round(e);
                }
            }), e.hide().animate(r, {
                duration: a,
                easing: u,
                complete: d,
                step: function(e, n) {
                    n.now = Math.round(e), "height" !== n.prop ? l += n.now : "content" !== f.options.heightStyle && (n.now = Math.round(o - t.outerHeight() - l), l = 0);
                }
            }), undefined) : t.animate(n, a, u, d) : e.animate(r, a, u, d);
        },
        _toggleComplete: function(e) {
            var t = e.oldPanel;
            t.removeClass("ui-accordion-content-active").prev().removeClass("ui-corner-top").addClass("ui-corner-all"), t.length && (t.parent()[0].className = t.parent()[0].className), this._trigger("activate", null, e);
        }
    });
})(jQuery);

(function(e) {
    var t = 0;
    e.widget("ui.autocomplete", {
        version: "1.10.3",
        defaultElement: "<input>",
        options: {
            appendTo: null,
            autoFocus: !1,
            delay: 300,
            minLength: 1,
            position: {
                my: "left top",
                at: "left bottom",
                collision: "none"
            },
            source: null,
            change: null,
            close: null,
            focus: null,
            open: null,
            response: null,
            search: null,
            select: null
        },
        pending: 0,
        _create: function() {
            var t, n, r, i = this.element[0].nodeName.toLowerCase(), s = "textarea" === i, o = "input" === i;
            this.isMultiLine = s ? !0 : o ? !1 : this.element.prop("isContentEditable"), this.valueMethod = this.element[s || o ? "val" : "text"], this.isNewMenu = !0, this.element.addClass("ui-autocomplete-input").attr("autocomplete", "off"), this._on(this.element, {
                keydown: function(i) {
                    if (this.element.prop("readOnly")) return t = !0, r = !0, n = !0, undefined;
                    t = !1, r = !1, n = !1;
                    var s = e.ui.keyCode;
                    switch (i.keyCode) {
                      case s.PAGE_UP:
                        t = !0, this._move("previousPage", i);
                        break;
                      case s.PAGE_DOWN:
                        t = !0, this._move("nextPage", i);
                        break;
                      case s.UP:
                        t = !0, this._keyEvent("previous", i);
                        break;
                      case s.DOWN:
                        t = !0, this._keyEvent("next", i);
                        break;
                      case s.ENTER:
                      case s.NUMPAD_ENTER:
                        this.menu.active && (t = !0, i.preventDefault(), this.menu.select(i));
                        break;
                      case s.TAB:
                        this.menu.active && this.menu.select(i);
                        break;
                      case s.ESCAPE:
                        this.menu.element.is(":visible") && (this._value(this.term), this.close(i), i.preventDefault());
                        break;
                      default:
                        n = !0, this._searchTimeout(i);
                    }
                },
                keypress: function(r) {
                    if (t) return t = !1, (!this.isMultiLine || this.menu.element.is(":visible")) && r.preventDefault(), undefined;
                    if (!n) {
                        var i = e.ui.keyCode;
                        switch (r.keyCode) {
                          case i.PAGE_UP:
                            this._move("previousPage", r);
                            break;
                          case i.PAGE_DOWN:
                            this._move("nextPage", r);
                            break;
                          case i.UP:
                            this._keyEvent("previous", r);
                            break;
                          case i.DOWN:
                            this._keyEvent("next", r);
                        }
                    }
                },
                input: function(e) {
                    return r ? (r = !1, e.preventDefault(), undefined) : (this._searchTimeout(e), undefined);
                },
                focus: function() {
                    this.selectedItem = null, this.previous = this._value();
                },
                blur: function(e) {
                    return this.cancelBlur ? (delete this.cancelBlur, undefined) : (clearTimeout(this.searching), this.close(e), this._change(e), undefined);
                }
            }), this._initSource(), this.menu = e("<ul>").addClass("ui-autocomplete ui-front").appendTo(this._appendTo()).menu({
                role: null
            }).hide().data("ui-menu"), this._on(this.menu.element, {
                mousedown: function(t) {
                    t.preventDefault(), this.cancelBlur = !0, this._delay(function() {
                        delete this.cancelBlur;
                    });
                    var n = this.menu.element[0];
                    e(t.target).closest(".ui-menu-item").length || this._delay(function() {
                        var t = this;
                        this.document.one("mousedown", function(r) {
                            r.target === t.element[0] || r.target === n || e.contains(n, r.target) || t.close();
                        });
                    });
                },
                menufocus: function(t, n) {
                    if (this.isNewMenu && (this.isNewMenu = !1, t.originalEvent && /^mouse/.test(t.originalEvent.type))) return this.menu.blur(), this.document.one("mousemove", function() {
                        e(t.target).trigger(t.originalEvent);
                    }), undefined;
                    var r = n.item.data("ui-autocomplete-item");
                    !1 !== this._trigger("focus", t, {
                        item: r
                    }) ? t.originalEvent && /^key/.test(t.originalEvent.type) && this._value(r.value) : this.liveRegion.text(r.value);
                },
                menuselect: function(e, t) {
                    var n = t.item.data("ui-autocomplete-item"), r = this.previous;
                    this.element[0] !== this.document[0].activeElement && (this.element.focus(), this.previous = r, this._delay(function() {
                        this.previous = r, this.selectedItem = n;
                    })), !1 !== this._trigger("select", e, {
                        item: n
                    }) && this._value(n.value), this.term = this._value(), this.close(e), this.selectedItem = n;
                }
            }), this.liveRegion = e("<span>", {
                role: "status",
                "aria-live": "polite"
            }).addClass("ui-helper-hidden-accessible").insertBefore(this.element), this._on(this.window, {
                beforeunload: function() {
                    this.element.removeAttr("autocomplete");
                }
            });
        },
        _destroy: function() {
            clearTimeout(this.searching), this.element.removeClass("ui-autocomplete-input").removeAttr("autocomplete"), this.menu.element.remove(), this.liveRegion.remove();
        },
        _setOption: function(e, t) {
            this._super(e, t), "source" === e && this._initSource(), "appendTo" === e && this.menu.element.appendTo(this._appendTo()), "disabled" === e && t && this.xhr && this.xhr.abort();
        },
        _appendTo: function() {
            var t = this.options.appendTo;
            return t && (t = t.jquery || t.nodeType ? e(t) : this.document.find(t).eq(0)), t || (t = this.element.closest(".ui-front")), t.length || (t = this.document[0].body), t;
        },
        _initSource: function() {
            var t, n, r = this;
            e.isArray(this.options.source) ? (t = this.options.source, this.source = function(n, r) {
                r(e.ui.autocomplete.filter(t, n.term));
            }) : "string" == typeof this.options.source ? (n = this.options.source, this.source = function(t, s) {
                r.xhr && r.xhr.abort(), r.xhr = e.ajax({
                    url: n,
                    data: t,
                    dataType: "json",
                    success: function(e) {
                        s(e);
                    },
                    error: function() {
                        s([]);
                    }
                });
            }) : this.source = this.options.source;
        },
        _searchTimeout: function(e) {
            clearTimeout(this.searching), this.searching = this._delay(function() {
                this.term !== this._value() && (this.selectedItem = null, this.search(null, e));
            }, this.options.delay);
        },
        search: function(e, t) {
            return e = null != e ? e : this._value(), this.term = this._value(), e.length < this.options.minLength ? this.close(t) : this._trigger("search", t) !== !1 ? this._search(e) : undefined;
        },
        _search: function(e) {
            this.pending++, this.element.addClass("ui-autocomplete-loading"), this.cancelSearch = !1, this.source({
                term: e
            }, this._response());
        },
        _response: function() {
            var e = this, n = ++t;
            return function(r) {
                n === t && e.__response(r), e.pending--, e.pending || e.element.removeClass("ui-autocomplete-loading");
            };
        },
        __response: function(e) {
            e && (e = this._normalize(e)), this._trigger("response", null, {
                content: e
            }), !this.options.disabled && e && e.length && !this.cancelSearch ? (this._suggest(e), this._trigger("open")) : this._close();
        },
        close: function(e) {
            this.cancelSearch = !0, this._close(e);
        },
        _close: function(e) {
            this.menu.element.is(":visible") && (this.menu.element.hide(), this.menu.blur(), this.isNewMenu = !0, this._trigger("close", e));
        },
        _change: function(e) {
            this.previous !== this._value() && this._trigger("change", e, {
                item: this.selectedItem
            });
        },
        _normalize: function(t) {
            return t.length && t[0].label && t[0].value ? t : e.map(t, function(t) {
                return "string" == typeof t ? {
                    label: t,
                    value: t
                } : e.extend({
                    label: t.label || t.value,
                    value: t.value || t.label
                }, t);
            });
        },
        _suggest: function(t) {
            var n = this.menu.element.empty();
            this._renderMenu(n, t), this.isNewMenu = !0, this.menu.refresh(), n.show(), this._resizeMenu(), n.position(e.extend({
                of: this.element
            }, this.options.position)), this.options.autoFocus && this.menu.next();
        },
        _resizeMenu: function() {
            var e = this.menu.element;
            e.outerWidth(Math.max(e.width("").outerWidth() + 1, this.element.outerWidth()));
        },
        _renderMenu: function(t, n) {
            var r = this;
            e.each(n, function(e, n) {
                r._renderItemData(t, n);
            });
        },
        _renderItemData: function(e, t) {
            return this._renderItem(e, t).data("ui-autocomplete-item", t);
        },
        _renderItem: function(t, n) {
            return e("<li>").append(e("<a>").text(n.label)).appendTo(t);
        },
        _move: function(e, t) {
            return this.menu.element.is(":visible") ? this.menu.isFirstItem() && /^previous/.test(e) || this.menu.isLastItem() && /^next/.test(e) ? (this._value(this.term), this.menu.blur(), undefined) : (this.menu[e](t), undefined) : (this.search(null, t), undefined);
        },
        widget: function() {
            return this.menu.element;
        },
        _value: function() {
            return this.valueMethod.apply(this.element, arguments);
        },
        _keyEvent: function(e, t) {
            (!this.isMultiLine || this.menu.element.is(":visible")) && (this._move(e, t), t.preventDefault());
        }
    }), e.extend(e.ui.autocomplete, {
        escapeRegex: function(e) {
            return e.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, "\\$&");
        },
        filter: function(t, n) {
            var r = RegExp(e.ui.autocomplete.escapeRegex(n), "i");
            return e.grep(t, function(e) {
                return r.test(e.label || e.value || e);
            });
        }
    }), e.widget("ui.autocomplete", e.ui.autocomplete, {
        options: {
            messages: {
                noResults: "No search results.",
                results: function(e) {
                    return e + (e > 1 ? " results are" : " result is") + " available, use up and down arrow keys to navigate.";
                }
            }
        },
        __response: function(e) {
            var t;
            this._superApply(arguments), this.options.disabled || this.cancelSearch || (t = e && e.length ? this.options.messages.results(e.length) : this.options.messages.noResults, this.liveRegion.text(t));
        }
    });
})(jQuery);

(function(e) {
    var t, n, r, i, s = "ui-button ui-widget ui-state-default ui-corner-all", o = "ui-state-hover ui-state-active ", u = "ui-button-icons-only ui-button-icon-only ui-button-text-icons ui-button-text-icon-primary ui-button-text-icon-secondary ui-button-text-only", a = function() {
        var t = e(this);
        setTimeout(function() {
            t.find(":ui-button").button("refresh");
        }, 1);
    }, f = function(t) {
        var n = t.name, r = t.form, i = e([]);
        return n && (n = n.replace(/'/g, "\\'"), i = r ? e(r).find("[name='" + n + "']") : e("[name='" + n + "']", t.ownerDocument).filter(function() {
            return !this.form;
        })), i;
    };
    e.widget("ui.button", {
        version: "1.10.3",
        defaultElement: "<button>",
        options: {
            disabled: null,
            text: !0,
            label: null,
            icons: {
                primary: null,
                secondary: null
            }
        },
        _create: function() {
            this.element.closest("form").unbind("reset" + this.eventNamespace).bind("reset" + this.eventNamespace, a), "boolean" != typeof this.options.disabled ? this.options.disabled = !!this.element.prop("disabled") : this.element.prop("disabled", this.options.disabled), this._determineButtonType(), this.hasTitle = !!this.buttonElement.attr("title");
            var o = this, u = this.options, l = "checkbox" === this.type || "radio" === this.type, c = l ? "" : "ui-state-active", p = "ui-state-focus";
            null === u.label && (u.label = "input" === this.type ? this.buttonElement.val() : this.buttonElement.html()), this._hoverable(this.buttonElement), this.buttonElement.addClass(s).attr("role", "button").bind("mouseenter" + this.eventNamespace, function() {
                u.disabled || this === t && e(this).addClass("ui-state-active");
            }).bind("mouseleave" + this.eventNamespace, function() {
                u.disabled || e(this).removeClass(c);
            }).bind("click" + this.eventNamespace, function(e) {
                u.disabled && (e.preventDefault(), e.stopImmediatePropagation());
            }), this.element.bind("focus" + this.eventNamespace, function() {
                o.buttonElement.addClass(p);
            }).bind("blur" + this.eventNamespace, function() {
                o.buttonElement.removeClass(p);
            }), l && (this.element.bind("change" + this.eventNamespace, function() {
                i || o.refresh();
            }), this.buttonElement.bind("mousedown" + this.eventNamespace, function(e) {
                u.disabled || (i = !1, n = e.pageX, r = e.pageY);
            }).bind("mouseup" + this.eventNamespace, function(e) {
                u.disabled || (n !== e.pageX || r !== e.pageY) && (i = !0);
            })), "checkbox" === this.type ? this.buttonElement.bind("click" + this.eventNamespace, function() {
                return u.disabled || i ? !1 : undefined;
            }) : "radio" === this.type ? this.buttonElement.bind("click" + this.eventNamespace, function() {
                if (u.disabled || i) return !1;
                e(this).addClass("ui-state-active"), o.buttonElement.attr("aria-pressed", "true");
                var t = o.element[0];
                f(t).not(t).map(function() {
                    return e(this).button("widget")[0];
                }).removeClass("ui-state-active").attr("aria-pressed", "false");
            }) : (this.buttonElement.bind("mousedown" + this.eventNamespace, function() {
                return u.disabled ? !1 : (e(this).addClass("ui-state-active"), t = this, o.document.one("mouseup", function() {
                    t = null;
                }), undefined);
            }).bind("mouseup" + this.eventNamespace, function() {
                return u.disabled ? !1 : (e(this).removeClass("ui-state-active"), undefined);
            }).bind("keydown" + this.eventNamespace, function(t) {
                return u.disabled ? !1 : ((t.keyCode === e.ui.keyCode.SPACE || t.keyCode === e.ui.keyCode.ENTER) && e(this).addClass("ui-state-active"), undefined);
            }).bind("keyup" + this.eventNamespace + " blur" + this.eventNamespace, function() {
                e(this).removeClass("ui-state-active");
            }), this.buttonElement.is("a") && this.buttonElement.keyup(function(t) {
                t.keyCode === e.ui.keyCode.SPACE && e(this).click();
            })), this._setOption("disabled", u.disabled), this._resetButton();
        },
        _determineButtonType: function() {
            var e, t, n;
            this.type = this.element.is("[type=checkbox]") ? "checkbox" : this.element.is("[type=radio]") ? "radio" : this.element.is("input") ? "input" : "button", "checkbox" === this.type || "radio" === this.type ? (e = this.element.parents().last(), t = "label[for='" + this.element.attr("id") + "']", this.buttonElement = e.find(t), this.buttonElement.length || (e = e.length ? e.siblings() : this.element.siblings(), this.buttonElement = e.filter(t), this.buttonElement.length || (this.buttonElement = e.find(t))), this.element.addClass("ui-helper-hidden-accessible"), n = this.element.is(":checked"), n && this.buttonElement.addClass("ui-state-active"), this.buttonElement.prop("aria-pressed", n)) : this.buttonElement = this.element;
        },
        widget: function() {
            return this.buttonElement;
        },
        _destroy: function() {
            this.element.removeClass("ui-helper-hidden-accessible"), this.buttonElement.removeClass(s + " " + o + " " + u).removeAttr("role").removeAttr("aria-pressed").html(this.buttonElement.find(".ui-button-text").html()), this.hasTitle || this.buttonElement.removeAttr("title");
        },
        _setOption: function(e, t) {
            return this._super(e, t), "disabled" === e ? (t ? this.element.prop("disabled", !0) : this.element.prop("disabled", !1), undefined) : (this._resetButton(), undefined);
        },
        refresh: function() {
            var t = this.element.is("input, button") ? this.element.is(":disabled") : this.element.hasClass("ui-button-disabled");
            t !== this.options.disabled && this._setOption("disabled", t), "radio" === this.type ? f(this.element[0]).each(function() {
                e(this).is(":checked") ? e(this).button("widget").addClass("ui-state-active").attr("aria-pressed", "true") : e(this).button("widget").removeClass("ui-state-active").attr("aria-pressed", "false");
            }) : "checkbox" === this.type && (this.element.is(":checked") ? this.buttonElement.addClass("ui-state-active").attr("aria-pressed", "true") : this.buttonElement.removeClass("ui-state-active").attr("aria-pressed", "false"));
        },
        _resetButton: function() {
            if ("input" === this.type) return this.options.label && this.element.val(this.options.label), undefined;
            var t = this.buttonElement.removeClass(u), n = e("<span></span>", this.document[0]).addClass("ui-button-text").html(this.options.label).appendTo(t.empty()).text(), r = this.options.icons, i = r.primary && r.secondary, s = [];
            r.primary || r.secondary ? (this.options.text && s.push("ui-button-text-icon" + (i ? "s" : r.primary ? "-primary" : "-secondary")), r.primary && t.prepend("<span class='ui-button-icon-primary ui-icon " + r.primary + "'></span>"), r.secondary && t.append("<span class='ui-button-icon-secondary ui-icon " + r.secondary + "'></span>"), this.options.text || (s.push(i ? "ui-button-icons-only" : "ui-button-icon-only"), this.hasTitle || t.attr("title", e.trim(n)))) : s.push("ui-button-text-only"), t.addClass(s.join(" "));
        }
    }), e.widget("ui.buttonset", {
        version: "1.10.3",
        options: {
            items: "button, input[type=button], input[type=submit], input[type=reset], input[type=checkbox], input[type=radio], a, :data(ui-button)"
        },
        _create: function() {
            this.element.addClass("ui-buttonset");
        },
        _init: function() {
            this.refresh();
        },
        _setOption: function(e, t) {
            "disabled" === e && this.buttons.button("option", e, t), this._super(e, t);
        },
        refresh: function() {
            var t = "rtl" === this.element.css("direction");
            this.buttons = this.element.find(this.options.items).filter(":ui-button").button("refresh").end().not(":ui-button").button().end().map(function() {
                return e(this).button("widget")[0];
            }).removeClass("ui-corner-all ui-corner-left ui-corner-right").filter(":first").addClass(t ? "ui-corner-right" : "ui-corner-left").end().filter(":last").addClass(t ? "ui-corner-left" : "ui-corner-right").end().end();
        },
        _destroy: function() {
            this.element.removeClass("ui-buttonset"), this.buttons.map(function() {
                return e(this).button("widget")[0];
            }).removeClass("ui-corner-left ui-corner-right").end().button("destroy");
        }
    });
})(jQuery);

(function(e, t) {
    function n() {
        this._curInst = null, this._keyEvent = !1, this._disabledInputs = [], this._datepickerShowing = !1, this._inDialog = !1, this._mainDivId = "ui-datepicker-div", this._inlineClass = "ui-datepicker-inline", this._appendClass = "ui-datepicker-append", this._triggerClass = "ui-datepicker-trigger", this._dialogClass = "ui-datepicker-dialog", this._disableClass = "ui-datepicker-disabled", this._unselectableClass = "ui-datepicker-unselectable", this._currentClass = "ui-datepicker-current-day", this._dayOverClass = "ui-datepicker-days-cell-over", this.regional = [], this.regional[""] = {
            closeText: "Done",
            prevText: "Prev",
            nextText: "Next",
            currentText: "Today",
            monthNames: [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ],
            monthNamesShort: [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ],
            dayNames: [ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" ],
            dayNamesShort: [ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ],
            dayNamesMin: [ "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" ],
            weekHeader: "Wk",
            dateFormat: "mm/dd/yy",
            firstDay: 0,
            isRTL: !1,
            showMonthAfterYear: !1,
            yearSuffix: ""
        }, this._defaults = {
            showOn: "focus",
            showAnim: "fadeIn",
            showOptions: {},
            defaultDate: null,
            appendText: "",
            buttonText: "...",
            buttonImage: "",
            buttonImageOnly: !1,
            hideIfNoPrevNext: !1,
            navigationAsDateFormat: !1,
            gotoCurrent: !1,
            changeMonth: !1,
            changeYear: !1,
            yearRange: "c-10:c+10",
            showOtherMonths: !1,
            selectOtherMonths: !1,
            showWeek: !1,
            calculateWeek: this.iso8601Week,
            shortYearCutoff: "+10",
            minDate: null,
            maxDate: null,
            duration: "fast",
            beforeShowDay: null,
            beforeShow: null,
            onSelect: null,
            onChangeMonthYear: null,
            onClose: null,
            numberOfMonths: 1,
            showCurrentAtPos: 0,
            stepMonths: 1,
            stepBigMonths: 12,
            altField: "",
            altFormat: "",
            constrainInput: !0,
            showButtonPanel: !1,
            autoSize: !1,
            disabled: !1
        }, e.extend(this._defaults, this.regional[""]), this.dpDiv = r(e("<div id='" + this._mainDivId + "' class='ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all'></div>"));
    }
    function r(t) {
        var n = "button, .ui-datepicker-prev, .ui-datepicker-next, .ui-datepicker-calendar td a";
        return t.delegate(n, "mouseout", function() {
            e(this).removeClass("ui-state-hover"), -1 !== this.className.indexOf("ui-datepicker-prev") && e(this).removeClass("ui-datepicker-prev-hover"), -1 !== this.className.indexOf("ui-datepicker-next") && e(this).removeClass("ui-datepicker-next-hover");
        }).delegate(n, "mouseover", function() {
            e.datepicker._isDisabledDatepicker(s.inline ? t.parent()[0] : s.input[0]) || (e(this).parents(".ui-datepicker-calendar").find("a").removeClass("ui-state-hover"), e(this).addClass("ui-state-hover"), -1 !== this.className.indexOf("ui-datepicker-prev") && e(this).addClass("ui-datepicker-prev-hover"), -1 !== this.className.indexOf("ui-datepicker-next") && e(this).addClass("ui-datepicker-next-hover"));
        });
    }
    function i(t, n) {
        e.extend(t, n);
        for (var r in n) null == n[r] && (t[r] = n[r]);
        return t;
    }
    e.extend(e.ui, {
        datepicker: {
            version: "1.10.3"
        }
    });
    var s, o = "datepicker";
    e.extend(n.prototype, {
        markerClassName: "hasDatepicker",
        maxRows: 4,
        _widgetDatepicker: function() {
            return this.dpDiv;
        },
        setDefaults: function(e) {
            return i(this._defaults, e || {}), this;
        },
        _attachDatepicker: function(t, n) {
            var r, i, s;
            r = t.nodeName.toLowerCase(), i = "div" === r || "span" === r, t.id || (this.uuid += 1, t.id = "dp" + this.uuid), s = this._newInst(e(t), i), s.settings = e.extend({}, n || {}), "input" === r ? this._connectDatepicker(t, s) : i && this._inlineDatepicker(t, s);
        },
        _newInst: function(t, n) {
            var i = t[0].id.replace(/([^A-Za-z0-9_\-])/g, "\\\\$1");
            return {
                id: i,
                input: t,
                selectedDay: 0,
                selectedMonth: 0,
                selectedYear: 0,
                drawMonth: 0,
                drawYear: 0,
                inline: n,
                dpDiv: n ? r(e("<div class='" + this._inlineClass + " ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all'></div>")) : this.dpDiv
            };
        },
        _connectDatepicker: function(t, n) {
            var r = e(t);
            n.append = e([]), n.trigger = e([]), r.hasClass(this.markerClassName) || (this._attachments(r, n), r.addClass(this.markerClassName).keydown(this._doKeyDown).keypress(this._doKeyPress).keyup(this._doKeyUp), this._autoSize(n), e.data(t, o, n), n.settings.disabled && this._disableDatepicker(t));
        },
        _attachments: function(t, n) {
            var r, i, s, o = this._get(n, "appendText"), u = this._get(n, "isRTL");
            n.append && n.append.remove(), o && (n.append = e("<span class='" + this._appendClass + "'>" + o + "</span>"), t[u ? "before" : "after"](n.append)), t.unbind("focus", this._showDatepicker), n.trigger && n.trigger.remove(), r = this._get(n, "showOn"), ("focus" === r || "both" === r) && t.focus(this._showDatepicker), ("button" === r || "both" === r) && (i = this._get(n, "buttonText"), s = this._get(n, "buttonImage"), n.trigger = e(this._get(n, "buttonImageOnly") ? e("<img/>").addClass(this._triggerClass).attr({
                src: s,
                alt: i,
                title: i
            }) : e("<button type='button'></button>").addClass(this._triggerClass).html(s ? e("<img/>").attr({
                src: s,
                alt: i,
                title: i
            }) : i)), t[u ? "before" : "after"](n.trigger), n.trigger.click(function() {
                return e.datepicker._datepickerShowing && e.datepicker._lastInput === t[0] ? e.datepicker._hideDatepicker() : e.datepicker._datepickerShowing && e.datepicker._lastInput !== t[0] ? (e.datepicker._hideDatepicker(), e.datepicker._showDatepicker(t[0])) : e.datepicker._showDatepicker(t[0]), !1;
            }));
        },
        _autoSize: function(e) {
            if (this._get(e, "autoSize") && !e.inline) {
                var t, n, r, i, s = new Date(2009, 11, 20), o = this._get(e, "dateFormat");
                o.match(/[DM]/) && (t = function(e) {
                    for (n = 0, r = 0, i = 0; e.length > i; i++) e[i].length > n && (n = e[i].length, r = i);
                    return r;
                }, s.setMonth(t(this._get(e, o.match(/MM/) ? "monthNames" : "monthNamesShort"))), s.setDate(t(this._get(e, o.match(/DD/) ? "dayNames" : "dayNamesShort")) + 20 - s.getDay())), e.input.attr("size", this._formatDate(e, s).length);
            }
        },
        _inlineDatepicker: function(t, n) {
            var r = e(t);
            r.hasClass(this.markerClassName) || (r.addClass(this.markerClassName).append(n.dpDiv), e.data(t, o, n), this._setDate(n, this._getDefaultDate(n), !0), this._updateDatepicker(n), this._updateAlternate(n), n.settings.disabled && this._disableDatepicker(t), n.dpDiv.css("display", "block"));
        },
        _dialogDatepicker: function(t, n, r, s, u) {
            var f, l, c, h, p, d = this._dialogInst;
            return d || (this.uuid += 1, f = "dp" + this.uuid, this._dialogInput = e("<input type='text' id='" + f + "' style='position: absolute; top: -100px; width: 0px;'/>"), this._dialogInput.keydown(this._doKeyDown), e("body").append(this._dialogInput), d = this._dialogInst = this._newInst(this._dialogInput, !1), d.settings = {}, e.data(this._dialogInput[0], o, d)), i(d.settings, s || {}), n = n && n.constructor === Date ? this._formatDate(d, n) : n, this._dialogInput.val(n), this._pos = u ? u.length ? u : [ u.pageX, u.pageY ] : null, this._pos || (l = document.documentElement.clientWidth, c = document.documentElement.clientHeight, h = document.documentElement.scrollLeft || document.body.scrollLeft, p = document.documentElement.scrollTop || document.body.scrollTop, this._pos = [ l / 2 - 100 + h, c / 2 - 150 + p ]), this._dialogInput.css("left", this._pos[0] + 20 + "px").css("top", this._pos[1] + "px"), d.settings.onSelect = r, this._inDialog = !0, this.dpDiv.addClass(this._dialogClass), this._showDatepicker(this._dialogInput[0]), e.blockUI && e.blockUI(this.dpDiv), e.data(this._dialogInput[0], o, d), this;
        },
        _destroyDatepicker: function(t) {
            var n, r = e(t), i = e.data(t, o);
            r.hasClass(this.markerClassName) && (n = t.nodeName.toLowerCase(), e.removeData(t, o), "input" === n ? (i.append.remove(), i.trigger.remove(), r.removeClass(this.markerClassName).unbind("focus", this._showDatepicker).unbind("keydown", this._doKeyDown).unbind("keypress", this._doKeyPress).unbind("keyup", this._doKeyUp)) : ("div" === n || "span" === n) && r.removeClass(this.markerClassName).empty());
        },
        _enableDatepicker: function(t) {
            var n, r, i = e(t), s = e.data(t, o);
            i.hasClass(this.markerClassName) && (n = t.nodeName.toLowerCase(), "input" === n ? (t.disabled = !1, s.trigger.filter("button").each(function() {
                this.disabled = !1;
            }).end().filter("img").css({
                opacity: "1.0",
                cursor: ""
            })) : ("div" === n || "span" === n) && (r = i.children("." + this._inlineClass), r.children().removeClass("ui-state-disabled"), r.find("select.ui-datepicker-month, select.ui-datepicker-year").prop("disabled", !1)), this._disabledInputs = e.map(this._disabledInputs, function(e) {
                return e === t ? null : e;
            }));
        },
        _disableDatepicker: function(t) {
            var n, r, i = e(t), s = e.data(t, o);
            i.hasClass(this.markerClassName) && (n = t.nodeName.toLowerCase(), "input" === n ? (t.disabled = !0, s.trigger.filter("button").each(function() {
                this.disabled = !0;
            }).end().filter("img").css({
                opacity: "0.5",
                cursor: "default"
            })) : ("div" === n || "span" === n) && (r = i.children("." + this._inlineClass), r.children().addClass("ui-state-disabled"), r.find("select.ui-datepicker-month, select.ui-datepicker-year").prop("disabled", !0)), this._disabledInputs = e.map(this._disabledInputs, function(e) {
                return e === t ? null : e;
            }), this._disabledInputs[this._disabledInputs.length] = t);
        },
        _isDisabledDatepicker: function(e) {
            if (!e) return !1;
            for (var t = 0; this._disabledInputs.length > t; t++) if (this._disabledInputs[t] === e) return !0;
            return !1;
        },
        _getInst: function(t) {
            try {
                return e.data(t, o);
            } catch (n) {
                throw "Missing instance data for this datepicker";
            }
        },
        _optionDatepicker: function(n, r, s) {
            var o, u, f, l, c = this._getInst(n);
            return 2 === arguments.length && "string" == typeof r ? "defaults" === r ? e.extend({}, e.datepicker._defaults) : c ? "all" === r ? e.extend({}, c.settings) : this._get(c, r) : null : (o = r || {}, "string" == typeof r && (o = {}, o[r] = s), c && (this._curInst === c && this._hideDatepicker(), u = this._getDateDatepicker(n, !0), f = this._getMinMaxDate(c, "min"), l = this._getMinMaxDate(c, "max"), i(c.settings, o), null !== f && o.dateFormat !== t && o.minDate === t && (c.settings.minDate = this._formatDate(c, f)), null !== l && o.dateFormat !== t && o.maxDate === t && (c.settings.maxDate = this._formatDate(c, l)), "disabled" in o && (o.disabled ? this._disableDatepicker(n) : this._enableDatepicker(n)), this._attachments(e(n), c), this._autoSize(c), this._setDate(c, u), this._updateAlternate(c), this._updateDatepicker(c)), t);
        },
        _changeDatepicker: function(e, t, n) {
            this._optionDatepicker(e, t, n);
        },
        _refreshDatepicker: function(e) {
            var t = this._getInst(e);
            t && this._updateDatepicker(t);
        },
        _setDateDatepicker: function(e, t) {
            var n = this._getInst(e);
            n && (this._setDate(n, t), this._updateDatepicker(n), this._updateAlternate(n));
        },
        _getDateDatepicker: function(e, t) {
            var n = this._getInst(e);
            return n && !n.inline && this._setDateFromField(n, t), n ? this._getDate(n) : null;
        },
        _doKeyDown: function(t) {
            var n, r, i, s = e.datepicker._getInst(t.target), o = !0, u = s.dpDiv.is(".ui-datepicker-rtl");
            if (s._keyEvent = !0, e.datepicker._datepickerShowing) switch (t.keyCode) {
              case 9:
                e.datepicker._hideDatepicker(), o = !1;
                break;
              case 13:
                return i = e("td." + e.datepicker._dayOverClass + ":not(." + e.datepicker._currentClass + ")", s.dpDiv), i[0] && e.datepicker._selectDay(t.target, s.selectedMonth, s.selectedYear, i[0]), n = e.datepicker._get(s, "onSelect"), n ? (r = e.datepicker._formatDate(s), n.apply(s.input ? s.input[0] : null, [ r, s ])) : e.datepicker._hideDatepicker(), !1;
              case 27:
                e.datepicker._hideDatepicker();
                break;
              case 33:
                e.datepicker._adjustDate(t.target, t.ctrlKey ? -e.datepicker._get(s, "stepBigMonths") : -e.datepicker._get(s, "stepMonths"), "M");
                break;
              case 34:
                e.datepicker._adjustDate(t.target, t.ctrlKey ? +e.datepicker._get(s, "stepBigMonths") : +e.datepicker._get(s, "stepMonths"), "M");
                break;
              case 35:
                (t.ctrlKey || t.metaKey) && e.datepicker._clearDate(t.target), o = t.ctrlKey || t.metaKey;
                break;
              case 36:
                (t.ctrlKey || t.metaKey) && e.datepicker._gotoToday(t.target), o = t.ctrlKey || t.metaKey;
                break;
              case 37:
                (t.ctrlKey || t.metaKey) && e.datepicker._adjustDate(t.target, u ? 1 : -1, "D"), o = t.ctrlKey || t.metaKey, t.originalEvent.altKey && e.datepicker._adjustDate(t.target, t.ctrlKey ? -e.datepicker._get(s, "stepBigMonths") : -e.datepicker._get(s, "stepMonths"), "M");
                break;
              case 38:
                (t.ctrlKey || t.metaKey) && e.datepicker._adjustDate(t.target, -7, "D"), o = t.ctrlKey || t.metaKey;
                break;
              case 39:
                (t.ctrlKey || t.metaKey) && e.datepicker._adjustDate(t.target, u ? -1 : 1, "D"), o = t.ctrlKey || t.metaKey, t.originalEvent.altKey && e.datepicker._adjustDate(t.target, t.ctrlKey ? +e.datepicker._get(s, "stepBigMonths") : +e.datepicker._get(s, "stepMonths"), "M");
                break;
              case 40:
                (t.ctrlKey || t.metaKey) && e.datepicker._adjustDate(t.target, 7, "D"), o = t.ctrlKey || t.metaKey;
                break;
              default:
                o = !1;
            } else 36 === t.keyCode && t.ctrlKey ? e.datepicker._showDatepicker(this) : o = !1;
            o && (t.preventDefault(), t.stopPropagation());
        },
        _doKeyPress: function(n) {
            var r, i, s = e.datepicker._getInst(n.target);
            return e.datepicker._get(s, "constrainInput") ? (r = e.datepicker._possibleChars(e.datepicker._get(s, "dateFormat")), i = String.fromCharCode(null == n.charCode ? n.keyCode : n.charCode), n.ctrlKey || n.metaKey || " " > i || !r || r.indexOf(i) > -1) : t;
        },
        _doKeyUp: function(t) {
            var n, r = e.datepicker._getInst(t.target);
            if (r.input.val() !== r.lastVal) try {
                n = e.datepicker.parseDate(e.datepicker._get(r, "dateFormat"), r.input ? r.input.val() : null, e.datepicker._getFormatConfig(r)), n && (e.datepicker._setDateFromField(r), e.datepicker._updateAlternate(r), e.datepicker._updateDatepicker(r));
            } catch (i) {}
            return !0;
        },
        _showDatepicker: function(t) {
            if (t = t.target || t, "input" !== t.nodeName.toLowerCase() && (t = e("input", t.parentNode)[0]), !e.datepicker._isDisabledDatepicker(t) && e.datepicker._lastInput !== t) {
                var n, r, s, o, u, f, l;
                n = e.datepicker._getInst(t), e.datepicker._curInst && e.datepicker._curInst !== n && (e.datepicker._curInst.dpDiv.stop(!0, !0), n && e.datepicker._datepickerShowing && e.datepicker._hideDatepicker(e.datepicker._curInst.input[0])), r = e.datepicker._get(n, "beforeShow"), s = r ? r.apply(t, [ t, n ]) : {}, s !== !1 && (i(n.settings, s), n.lastVal = null, e.datepicker._lastInput = t, e.datepicker._setDateFromField(n), e.datepicker._inDialog && (t.value = ""), e.datepicker._pos || (e.datepicker._pos = e.datepicker._findPos(t), e.datepicker._pos[1] += t.offsetHeight), o = !1, e(t).parents().each(function() {
                    return o |= "fixed" === e(this).css("position"), !o;
                }), u = {
                    left: e.datepicker._pos[0],
                    top: e.datepicker._pos[1]
                }, e.datepicker._pos = null, n.dpDiv.empty(), n.dpDiv.css({
                    position: "absolute",
                    display: "block",
                    top: "-1000px"
                }), e.datepicker._updateDatepicker(n), u = e.datepicker._checkOffset(n, u, o), n.dpDiv.css({
                    position: e.datepicker._inDialog && e.blockUI ? "static" : o ? "fixed" : "absolute",
                    display: "none",
                    left: u.left + "px",
                    top: u.top + "px"
                }), n.inline || (f = e.datepicker._get(n, "showAnim"), l = e.datepicker._get(n, "duration"), n.dpDiv.zIndex(e(t).zIndex() + 1), e.datepicker._datepickerShowing = !0, e.effects && e.effects.effect[f] ? n.dpDiv.show(f, e.datepicker._get(n, "showOptions"), l) : n.dpDiv[f || "show"](f ? l : null), e.datepicker._shouldFocusInput(n) && n.input.focus(), e.datepicker._curInst = n));
            }
        },
        _updateDatepicker: function(t) {
            this.maxRows = 4, s = t, t.dpDiv.empty().append(this._generateHTML(t)), this._attachHandlers(t), t.dpDiv.find("." + this._dayOverClass + " a").mouseover();
            var n, r = this._getNumberOfMonths(t), i = r[1], o = 17;
            t.dpDiv.removeClass("ui-datepicker-multi-2 ui-datepicker-multi-3 ui-datepicker-multi-4").width(""), i > 1 && t.dpDiv.addClass("ui-datepicker-multi-" + i).css("width", o * i + "em"), t.dpDiv[(1 !== r[0] || 1 !== r[1] ? "add" : "remove") + "Class"]("ui-datepicker-multi"), t.dpDiv[(this._get(t, "isRTL") ? "add" : "remove") + "Class"]("ui-datepicker-rtl"), t === e.datepicker._curInst && e.datepicker._datepickerShowing && e.datepicker._shouldFocusInput(t) && t.input.focus(), t.yearshtml && (n = t.yearshtml, setTimeout(function() {
                n === t.yearshtml && t.yearshtml && t.dpDiv.find("select.ui-datepicker-year:first").replaceWith(t.yearshtml), n = t.yearshtml = null;
            }, 0));
        },
        _shouldFocusInput: function(e) {
            return e.input && e.input.is(":visible") && !e.input.is(":disabled") && !e.input.is(":focus");
        },
        _checkOffset: function(t, n, r) {
            var i = t.dpDiv.outerWidth(), s = t.dpDiv.outerHeight(), o = t.input ? t.input.outerWidth() : 0, u = t.input ? t.input.outerHeight() : 0, a = document.documentElement.clientWidth + (r ? 0 : e(document).scrollLeft()), f = document.documentElement.clientHeight + (r ? 0 : e(document).scrollTop());
            return n.left -= this._get(t, "isRTL") ? i - o : 0, n.left -= r && n.left === t.input.offset().left ? e(document).scrollLeft() : 0, n.top -= r && n.top === t.input.offset().top + u ? e(document).scrollTop() : 0, n.left -= Math.min(n.left, n.left + i > a && a > i ? Math.abs(n.left + i - a) : 0), n.top -= Math.min(n.top, n.top + s > f && f > s ? Math.abs(s + u) : 0), n;
        },
        _findPos: function(t) {
            for (var n, r = this._getInst(t), i = this._get(r, "isRTL"); t && ("hidden" === t.type || 1 !== t.nodeType || e.expr.filters.hidden(t)); ) t = t[i ? "previousSibling" : "nextSibling"];
            return n = e(t).offset(), [ n.left, n.top ];
        },
        _hideDatepicker: function(t) {
            var n, r, i, s, u = this._curInst;
            !u || t && u !== e.data(t, o) || this._datepickerShowing && (n = this._get(u, "showAnim"), r = this._get(u, "duration"), i = function() {
                e.datepicker._tidyDialog(u);
            }, e.effects && (e.effects.effect[n] || e.effects[n]) ? u.dpDiv.hide(n, e.datepicker._get(u, "showOptions"), r, i) : u.dpDiv["slideDown" === n ? "slideUp" : "fadeIn" === n ? "fadeOut" : "hide"](n ? r : null, i), n || i(), this._datepickerShowing = !1, s = this._get(u, "onClose"), s && s.apply(u.input ? u.input[0] : null, [ u.input ? u.input.val() : "", u ]), this._lastInput = null, this._inDialog && (this._dialogInput.css({
                position: "absolute",
                left: "0",
                top: "-100px"
            }), e.blockUI && (e.unblockUI(), e("body").append(this.dpDiv))), this._inDialog = !1);
        },
        _tidyDialog: function(e) {
            e.dpDiv.removeClass(this._dialogClass).unbind(".ui-datepicker-calendar");
        },
        _checkExternalClick: function(t) {
            if (e.datepicker._curInst) {
                var n = e(t.target), r = e.datepicker._getInst(n[0]);
                (n[0].id !== e.datepicker._mainDivId && 0 === n.parents("#" + e.datepicker._mainDivId).length && !n.hasClass(e.datepicker.markerClassName) && !n.closest("." + e.datepicker._triggerClass).length && e.datepicker._datepickerShowing && (!e.datepicker._inDialog || !e.blockUI) || n.hasClass(e.datepicker.markerClassName) && e.datepicker._curInst !== r) && e.datepicker._hideDatepicker();
            }
        },
        _adjustDate: function(t, n, r) {
            var i = e(t), s = this._getInst(i[0]);
            this._isDisabledDatepicker(i[0]) || (this._adjustInstDate(s, n + ("M" === r ? this._get(s, "showCurrentAtPos") : 0), r), this._updateDatepicker(s));
        },
        _gotoToday: function(t) {
            var n, r = e(t), i = this._getInst(r[0]);
            this._get(i, "gotoCurrent") && i.currentDay ? (i.selectedDay = i.currentDay, i.drawMonth = i.selectedMonth = i.currentMonth, i.drawYear = i.selectedYear = i.currentYear) : (n = new Date, i.selectedDay = n.getDate(), i.drawMonth = i.selectedMonth = n.getMonth(), i.drawYear = i.selectedYear = n.getFullYear()), this._notifyChange(i), this._adjustDate(r);
        },
        _selectMonthYear: function(t, n, r) {
            var i = e(t), s = this._getInst(i[0]);
            s["selected" + ("M" === r ? "Month" : "Year")] = s["draw" + ("M" === r ? "Month" : "Year")] = parseInt(n.options[n.selectedIndex].value, 10), this._notifyChange(s), this._adjustDate(i);
        },
        _selectDay: function(t, n, r, i) {
            var s, o = e(t);
            e(i).hasClass(this._unselectableClass) || this._isDisabledDatepicker(o[0]) || (s = this._getInst(o[0]), s.selectedDay = s.currentDay = e("a", i).html(), s.selectedMonth = s.currentMonth = n, s.selectedYear = s.currentYear = r, this._selectDate(t, this._formatDate(s, s.currentDay, s.currentMonth, s.currentYear)));
        },
        _clearDate: function(t) {
            var n = e(t);
            this._selectDate(n, "");
        },
        _selectDate: function(t, n) {
            var r, i = e(t), s = this._getInst(i[0]);
            n = null != n ? n : this._formatDate(s), s.input && s.input.val(n), this._updateAlternate(s), r = this._get(s, "onSelect"), r ? r.apply(s.input ? s.input[0] : null, [ n, s ]) : s.input && s.input.trigger("change"), s.inline ? this._updateDatepicker(s) : (this._hideDatepicker(), this._lastInput = s.input[0], "object" != typeof s.input[0] && s.input.focus(), this._lastInput = null);
        },
        _updateAlternate: function(t) {
            var n, r, i, s = this._get(t, "altField");
            s && (n = this._get(t, "altFormat") || this._get(t, "dateFormat"), r = this._getDate(t), i = this.formatDate(n, r, this._getFormatConfig(t)), e(s).each(function() {
                e(this).val(i);
            }));
        },
        noWeekends: function(e) {
            var t = e.getDay();
            return [ t > 0 && 6 > t, "" ];
        },
        iso8601Week: function(e) {
            var t, n = new Date(e.getTime());
            return n.setDate(n.getDate() + 4 - (n.getDay() || 7)), t = n.getTime(), n.setMonth(0), n.setDate(1), Math.floor(Math.round((t - n) / 864e5) / 7) + 1;
        },
        parseDate: function(n, r, i) {
            if (null == n || null == r) throw "Invalid arguments";
            if (r = "object" == typeof r ? "" + r : r + "", "" === r) return null;
            var s, o, u, a, f = 0, l = (i ? i.shortYearCutoff : null) || this._defaults.shortYearCutoff, c = "string" != typeof l ? l : (new Date).getFullYear() % 100 + parseInt(l, 10), h = (i ? i.dayNamesShort : null) || this._defaults.dayNamesShort, p = (i ? i.dayNames : null) || this._defaults.dayNames, d = (i ? i.monthNamesShort : null) || this._defaults.monthNamesShort, v = (i ? i.monthNames : null) || this._defaults.monthNames, m = -1, g = -1, y = -1, b = -1, w = !1, E = function(e) {
                var t = n.length > s + 1 && n.charAt(s + 1) === e;
                return t && s++, t;
            }, S = function(e) {
                var t = E(e), n = "@" === e ? 14 : "!" === e ? 20 : "y" === e && t ? 4 : "o" === e ? 3 : 2, i = RegExp("^\\d{1," + n + "}"), s = r.substring(f).match(i);
                if (!s) throw "Missing number at position " + f;
                return f += s[0].length, parseInt(s[0], 10);
            }, x = function(n, i, s) {
                var o = -1, u = e.map(E(n) ? s : i, function(e, t) {
                    return [ [ t, e ] ];
                }).sort(function(e, t) {
                    return -(e[1].length - t[1].length);
                });
                if (e.each(u, function(e, n) {
                    var i = n[1];
                    return r.substr(f, i.length).toLowerCase() === i.toLowerCase() ? (o = n[0], f += i.length, !1) : t;
                }), -1 !== o) return o + 1;
                throw "Unknown name at position " + f;
            }, T = function() {
                if (r.charAt(f) !== n.charAt(s)) throw "Unexpected literal at position " + f;
                f++;
            };
            for (s = 0; n.length > s; s++) if (w) "'" !== n.charAt(s) || E("'") ? T() : w = !1; else switch (n.charAt(s)) {
              case "d":
                y = S("d");
                break;
              case "D":
                x("D", h, p);
                break;
              case "o":
                b = S("o");
                break;
              case "m":
                g = S("m");
                break;
              case "M":
                g = x("M", d, v);
                break;
              case "y":
                m = S("y");
                break;
              case "@":
                a = new Date(S("@")), m = a.getFullYear(), g = a.getMonth() + 1, y = a.getDate();
                break;
              case "!":
                a = new Date((S("!") - this._ticksTo1970) / 1e4), m = a.getFullYear(), g = a.getMonth() + 1, y = a.getDate();
                break;
              case "'":
                E("'") ? T() : w = !0;
                break;
              default:
                T();
            }
            if (r.length > f && (u = r.substr(f), !/^\s+/.test(u))) throw "Extra/unparsed characters found in date: " + u;
            if (-1 === m ? m = (new Date).getFullYear() : 100 > m && (m += (new Date).getFullYear() - (new Date).getFullYear() % 100 + (c >= m ? 0 : -100)), b > -1) for (g = 1, y = b; ; ) {
                if (o = this._getDaysInMonth(m, g - 1), o >= y) break;
                g++, y -= o;
            }
            if (a = this._daylightSavingAdjust(new Date(m, g - 1, y)), a.getFullYear() !== m || a.getMonth() + 1 !== g || a.getDate() !== y) throw "Invalid date";
            return a;
        },
        ATOM: "yy-mm-dd",
        COOKIE: "D, dd M yy",
        ISO_8601: "yy-mm-dd",
        RFC_822: "D, d M y",
        RFC_850: "DD, dd-M-y",
        RFC_1036: "D, d M y",
        RFC_1123: "D, d M yy",
        RFC_2822: "D, d M yy",
        RSS: "D, d M y",
        TICKS: "!",
        TIMESTAMP: "@",
        W3C: "yy-mm-dd",
        _ticksTo1970: 864e9 * (718685 + Math.floor(492.5) - Math.floor(19.7) + Math.floor(4.925)),
        formatDate: function(e, t, n) {
            if (!t) return "";
            var r, i = (n ? n.dayNamesShort : null) || this._defaults.dayNamesShort, s = (n ? n.dayNames : null) || this._defaults.dayNames, o = (n ? n.monthNamesShort : null) || this._defaults.monthNamesShort, u = (n ? n.monthNames : null) || this._defaults.monthNames, a = function(t) {
                var n = e.length > r + 1 && e.charAt(r + 1) === t;
                return n && r++, n;
            }, f = function(e, t, n) {
                var r = "" + t;
                if (a(e)) for (; n > r.length; ) r = "0" + r;
                return r;
            }, l = function(e, t, n, r) {
                return a(e) ? r[t] : n[t];
            }, c = "", h = !1;
            if (t) for (r = 0; e.length > r; r++) if (h) "'" !== e.charAt(r) || a("'") ? c += e.charAt(r) : h = !1; else switch (e.charAt(r)) {
              case "d":
                c += f("d", t.getDate(), 2);
                break;
              case "D":
                c += l("D", t.getDay(), i, s);
                break;
              case "o":
                c += f("o", Math.round(((new Date(t.getFullYear(), t.getMonth(), t.getDate())).getTime() - (new Date(t.getFullYear(), 0, 0)).getTime()) / 864e5), 3);
                break;
              case "m":
                c += f("m", t.getMonth() + 1, 2);
                break;
              case "M":
                c += l("M", t.getMonth(), o, u);
                break;
              case "y":
                c += a("y") ? t.getFullYear() : (10 > t.getYear() % 100 ? "0" : "") + t.getYear() % 100;
                break;
              case "@":
                c += t.getTime();
                break;
              case "!":
                c += 1e4 * t.getTime() + this._ticksTo1970;
                break;
              case "'":
                a("'") ? c += "'" : h = !0;
                break;
              default:
                c += e.charAt(r);
            }
            return c;
        },
        _possibleChars: function(e) {
            var t, n = "", r = !1, i = function(n) {
                var r = e.length > t + 1 && e.charAt(t + 1) === n;
                return r && t++, r;
            };
            for (t = 0; e.length > t; t++) if (r) "'" !== e.charAt(t) || i("'") ? n += e.charAt(t) : r = !1; else switch (e.charAt(t)) {
              case "d":
              case "m":
              case "y":
              case "@":
                n += "0123456789";
                break;
              case "D":
              case "M":
                return null;
              case "'":
                i("'") ? n += "'" : r = !0;
                break;
              default:
                n += e.charAt(t);
            }
            return n;
        },
        _get: function(e, n) {
            return e.settings[n] !== t ? e.settings[n] : this._defaults[n];
        },
        _setDateFromField: function(e, t) {
            if (e.input.val() !== e.lastVal) {
                var n = this._get(e, "dateFormat"), r = e.lastVal = e.input ? e.input.val() : null, i = this._getDefaultDate(e), s = i, o = this._getFormatConfig(e);
                try {
                    s = this.parseDate(n, r, o) || i;
                } catch (u) {
                    r = t ? "" : r;
                }
                e.selectedDay = s.getDate(), e.drawMonth = e.selectedMonth = s.getMonth(), e.drawYear = e.selectedYear = s.getFullYear(), e.currentDay = r ? s.getDate() : 0, e.currentMonth = r ? s.getMonth() : 0, e.currentYear = r ? s.getFullYear() : 0, this._adjustInstDate(e);
            }
        },
        _getDefaultDate: function(e) {
            return this._restrictMinMax(e, this._determineDate(e, this._get(e, "defaultDate"), new Date));
        },
        _determineDate: function(t, n, r) {
            var i = function(e) {
                var t = new Date;
                return t.setDate(t.getDate() + e), t;
            }, s = function(n) {
                try {
                    return e.datepicker.parseDate(e.datepicker._get(t, "dateFormat"), n, e.datepicker._getFormatConfig(t));
                } catch (r) {}
                for (var i = (n.toLowerCase().match(/^c/) ? e.datepicker._getDate(t) : null) || new Date, s = i.getFullYear(), o = i.getMonth(), u = i.getDate(), a = /([+\-]?[0-9]+)\s*(d|D|w|W|m|M|y|Y)?/g, f = a.exec(n); f; ) {
                    switch (f[2] || "d") {
                      case "d":
                      case "D":
                        u += parseInt(f[1], 10);
                        break;
                      case "w":
                      case "W":
                        u += 7 * parseInt(f[1], 10);
                        break;
                      case "m":
                      case "M":
                        o += parseInt(f[1], 10), u = Math.min(u, e.datepicker._getDaysInMonth(s, o));
                        break;
                      case "y":
                      case "Y":
                        s += parseInt(f[1], 10), u = Math.min(u, e.datepicker._getDaysInMonth(s, o));
                    }
                    f = a.exec(n);
                }
                return new Date(s, o, u);
            }, o = null == n || "" === n ? r : "string" == typeof n ? s(n) : "number" == typeof n ? isNaN(n) ? r : i(n) : new Date(n.getTime());
            return o = o && "Invalid Date" == "" + o ? r : o, o && (o.setHours(0), o.setMinutes(0), o.setSeconds(0), o.setMilliseconds(0)), this._daylightSavingAdjust(o);
        },
        _daylightSavingAdjust: function(e) {
            return e ? (e.setHours(e.getHours() > 12 ? e.getHours() + 2 : 0), e) : null;
        },
        _setDate: function(e, t, n) {
            var r = !t, i = e.selectedMonth, s = e.selectedYear, o = this._restrictMinMax(e, this._determineDate(e, t, new Date));
            e.selectedDay = e.currentDay = o.getDate(), e.drawMonth = e.selectedMonth = e.currentMonth = o.getMonth(), e.drawYear = e.selectedYear = e.currentYear = o.getFullYear(), i === e.selectedMonth && s === e.selectedYear || n || this._notifyChange(e), this._adjustInstDate(e), e.input && e.input.val(r ? "" : this._formatDate(e));
        },
        _getDate: function(e) {
            var t = !e.currentYear || e.input && "" === e.input.val() ? null : this._daylightSavingAdjust(new Date(e.currentYear, e.currentMonth, e.currentDay));
            return t;
        },
        _attachHandlers: function(t) {
            var n = this._get(t, "stepMonths"), r = "#" + t.id.replace(/\\\\/g, "\\");
            t.dpDiv.find("[data-handler]").map(function() {
                var t = {
                    prev: function() {
                        e.datepicker._adjustDate(r, -n, "M");
                    },
                    next: function() {
                        e.datepicker._adjustDate(r, +n, "M");
                    },
                    hide: function() {
                        e.datepicker._hideDatepicker();
                    },
                    today: function() {
                        e.datepicker._gotoToday(r);
                    },
                    selectDay: function() {
                        return e.datepicker._selectDay(r, +this.getAttribute("data-month"), +this.getAttribute("data-year"), this), !1;
                    },
                    selectMonth: function() {
                        return e.datepicker._selectMonthYear(r, this, "M"), !1;
                    },
                    selectYear: function() {
                        return e.datepicker._selectMonthYear(r, this, "Y"), !1;
                    }
                };
                e(this).bind(this.getAttribute("data-event"), t[this.getAttribute("data-handler")]);
            });
        },
        _generateHTML: function(e) {
            var t, n, r, i, s, o, u, a, f, l, c, h, p, d, v, m, g, y, b, w, E, S, x, T, N, C, k, L, A, O, M, _, D, P, H, B, j, F, I, q = new Date, R = this._daylightSavingAdjust(new Date(q.getFullYear(), q.getMonth(), q.getDate())), U = this._get(e, "isRTL"), z = this._get(e, "showButtonPanel"), W = this._get(e, "hideIfNoPrevNext"), X = this._get(e, "navigationAsDateFormat"), V = this._getNumberOfMonths(e), $ = this._get(e, "showCurrentAtPos"), J = this._get(e, "stepMonths"), K = 1 !== V[0] || 1 !== V[1], Q = this._daylightSavingAdjust(e.currentDay ? new Date(e.currentYear, e.currentMonth, e.currentDay) : new Date(9999, 9, 9)), G = this._getMinMaxDate(e, "min"), Y = this._getMinMaxDate(e, "max"), Z = e.drawMonth - $, et = e.drawYear;
            if (0 > Z && (Z += 12, et--), Y) for (t = this._daylightSavingAdjust(new Date(Y.getFullYear(), Y.getMonth() - V[0] * V[1] + 1, Y.getDate())), t = G && G > t ? G : t; this._daylightSavingAdjust(new Date(et, Z, 1)) > t; ) Z--, 0 > Z && (Z = 11, et--);
            for (e.drawMonth = Z, e.drawYear = et, n = this._get(e, "prevText"), n = X ? this.formatDate(n, this._daylightSavingAdjust(new Date(et, Z - J, 1)), this._getFormatConfig(e)) : n, r = this._canAdjustMonth(e, -1, et, Z) ? "<a class='ui-datepicker-prev ui-corner-all' data-handler='prev' data-event='click' title='" + n + "'><span class='ui-icon ui-icon-circle-triangle-" + (U ? "e" : "w") + "'>" + n + "</span></a>" : W ? "" : "<a class='ui-datepicker-prev ui-corner-all ui-state-disabled' title='" + n + "'><span class='ui-icon ui-icon-circle-triangle-" + (U ? "e" : "w") + "'>" + n + "</span></a>", i = this._get(e, "nextText"), i = X ? this.formatDate(i, this._daylightSavingAdjust(new Date(et, Z + J, 1)), this._getFormatConfig(e)) : i, s = this._canAdjustMonth(e, 1, et, Z) ? "<a class='ui-datepicker-next ui-corner-all' data-handler='next' data-event='click' title='" + i + "'><span class='ui-icon ui-icon-circle-triangle-" + (U ? "w" : "e") + "'>" + i + "</span></a>" : W ? "" : "<a class='ui-datepicker-next ui-corner-all ui-state-disabled' title='" + i + "'><span class='ui-icon ui-icon-circle-triangle-" + (U ? "w" : "e") + "'>" + i + "</span></a>", o = this._get(e, "currentText"), u = this._get(e, "gotoCurrent") && e.currentDay ? Q : R, o = X ? this.formatDate(o, u, this._getFormatConfig(e)) : o, a = e.inline ? "" : "<button type='button' class='ui-datepicker-close ui-state-default ui-priority-primary ui-corner-all' data-handler='hide' data-event='click'>" + this._get(e, "closeText") + "</button>", f = z ? "<div class='ui-datepicker-buttonpane ui-widget-content'>" + (U ? a : "") + (this._isInRange(e, u) ? "<button type='button' class='ui-datepicker-current ui-state-default ui-priority-secondary ui-corner-all' data-handler='today' data-event='click'>" + o + "</button>" : "") + (U ? "" : a) + "</div>" : "", l = parseInt(this._get(e, "firstDay"), 10), l = isNaN(l) ? 0 : l, c = this._get(e, "showWeek"), h = this._get(e, "dayNames"), p = this._get(e, "dayNamesMin"), d = this._get(e, "monthNames"), v = this._get(e, "monthNamesShort"), m = this._get(e, "beforeShowDay"), g = this._get(e, "showOtherMonths"), y = this._get(e, "selectOtherMonths"), b = this._getDefaultDate(e), w = "", S = 0; V[0] > S; S++) {
                for (x = "", this.maxRows = 4, T = 0; V[1] > T; T++) {
                    if (N = this._daylightSavingAdjust(new Date(et, Z, e.selectedDay)), C = " ui-corner-all", k = "", K) {
                        if (k += "<div class='ui-datepicker-group", V[1] > 1) switch (T) {
                          case 0:
                            k += " ui-datepicker-group-first", C = " ui-corner-" + (U ? "right" : "left");
                            break;
                          case V[1] - 1:
                            k += " ui-datepicker-group-last", C = " ui-corner-" + (U ? "left" : "right");
                            break;
                          default:
                            k += " ui-datepicker-group-middle", C = "";
                        }
                        k += "'>";
                    }
                    for (k += "<div class='ui-datepicker-header ui-widget-header ui-helper-clearfix" + C + "'>" + (/all|left/.test(C) && 0 === S ? U ? s : r : "") + (/all|right/.test(C) && 0 === S ? U ? r : s : "") + this._generateMonthYearHeader(e, Z, et, G, Y, S > 0 || T > 0, d, v) + "</div><table class='ui-datepicker-calendar'><thead>" + "<tr>", L = c ? "<th class='ui-datepicker-week-col'>" + this._get(e, "weekHeader") + "</th>" : "", E = 0; 7 > E; E++) A = (E + l) % 7, L += "<th" + ((E + l + 6) % 7 >= 5 ? " class='ui-datepicker-week-end'" : "") + ">" + "<span title='" + h[A] + "'>" + p[A] + "</span></th>";
                    for (k += L + "</tr></thead><tbody>", O = this._getDaysInMonth(et, Z), et === e.selectedYear && Z === e.selectedMonth && (e.selectedDay = Math.min(e.selectedDay, O)), M = (this._getFirstDayOfMonth(et, Z) - l + 7) % 7, _ = Math.ceil((M + O) / 7), D = K ? this.maxRows > _ ? this.maxRows : _ : _, this.maxRows = D, P = this._daylightSavingAdjust(new Date(et, Z, 1 - M)), H = 0; D > H; H++) {
                        for (k += "<tr>", B = c ? "<td class='ui-datepicker-week-col'>" + this._get(e, "calculateWeek")(P) + "</td>" : "", E = 0; 7 > E; E++) j = m ? m.apply(e.input ? e.input[0] : null, [ P ]) : [ !0, "" ], F = P.getMonth() !== Z, I = F && !y || !j[0] || G && G > P || Y && P > Y, B += "<td class='" + ((E + l + 6) % 7 >= 5 ? " ui-datepicker-week-end" : "") + (F ? " ui-datepicker-other-month" : "") + (P.getTime() === N.getTime() && Z === e.selectedMonth && e._keyEvent || b.getTime() === P.getTime() && b.getTime() === N.getTime() ? " " + this._dayOverClass : "") + (I ? " " + this._unselectableClass + " ui-state-disabled" : "") + (F && !g ? "" : " " + j[1] + (P.getTime() === Q.getTime() ? " " + this._currentClass : "") + (P.getTime() === R.getTime() ? " ui-datepicker-today" : "")) + "'" + (F && !g || !j[2] ? "" : " title='" + j[2].replace(/'/g, "&#39;") + "'") + (I ? "" : " data-handler='selectDay' data-event='click' data-month='" + P.getMonth() + "' data-year='" + P.getFullYear() + "'") + ">" + (F && !g ? "&#xa0;" : I ? "<span class='ui-state-default'>" + P.getDate() + "</span>" : "<a class='ui-state-default" + (P.getTime() === R.getTime() ? " ui-state-highlight" : "") + (P.getTime() === Q.getTime() ? " ui-state-active" : "") + (F ? " ui-priority-secondary" : "") + "' href='#'>" + P.getDate() + "</a>") + "</td>", P.setDate(P.getDate() + 1), P = this._daylightSavingAdjust(P);
                        k += B + "</tr>";
                    }
                    Z++, Z > 11 && (Z = 0, et++), k += "</tbody></table>" + (K ? "</div>" + (V[0] > 0 && T === V[1] - 1 ? "<div class='ui-datepicker-row-break'></div>" : "") : ""), x += k;
                }
                w += x;
            }
            return w += f, e._keyEvent = !1, w;
        },
        _generateMonthYearHeader: function(e, t, n, r, i, s, o, u) {
            var a, f, l, c, h, p, d, v, m = this._get(e, "changeMonth"), g = this._get(e, "changeYear"), y = this._get(e, "showMonthAfterYear"), b = "<div class='ui-datepicker-title'>", w = "";
            if (s || !m) w += "<span class='ui-datepicker-month'>" + o[t] + "</span>"; else {
                for (a = r && r.getFullYear() === n, f = i && i.getFullYear() === n, w += "<select class='ui-datepicker-month' data-handler='selectMonth' data-event='change'>", l = 0; 12 > l; l++) (!a || l >= r.getMonth()) && (!f || i.getMonth() >= l) && (w += "<option value='" + l + "'" + (l === t ? " selected='selected'" : "") + ">" + u[l] + "</option>");
                w += "</select>";
            }
            if (y || (b += w + (!s && m && g ? "" : "&#xa0;")), !e.yearshtml) if (e.yearshtml = "", s || !g) b += "<span class='ui-datepicker-year'>" + n + "</span>"; else {
                for (c = this._get(e, "yearRange").split(":"), h = (new Date).getFullYear(), p = function(e) {
                    var t = e.match(/c[+\-].*/) ? n + parseInt(e.substring(1), 10) : e.match(/[+\-].*/) ? h + parseInt(e, 10) : parseInt(e, 10);
                    return isNaN(t) ? h : t;
                }, d = p(c[0]), v = Math.max(d, p(c[1] || "")), d = r ? Math.max(d, r.getFullYear()) : d, v = i ? Math.min(v, i.getFullYear()) : v, e.yearshtml += "<select class='ui-datepicker-year' data-handler='selectYear' data-event='change'>"; v >= d; d++) e.yearshtml += "<option value='" + d + "'" + (d === n ? " selected='selected'" : "") + ">" + d + "</option>";
                e.yearshtml += "</select>", b += e.yearshtml, e.yearshtml = null;
            }
            return b += this._get(e, "yearSuffix"), y && (b += (!s && m && g ? "" : "&#xa0;") + w), b += "</div>";
        },
        _adjustInstDate: function(e, t, n) {
            var r = e.drawYear + ("Y" === n ? t : 0), i = e.drawMonth + ("M" === n ? t : 0), s = Math.min(e.selectedDay, this._getDaysInMonth(r, i)) + ("D" === n ? t : 0), o = this._restrictMinMax(e, this._daylightSavingAdjust(new Date(r, i, s)));
            e.selectedDay = o.getDate(), e.drawMonth = e.selectedMonth = o.getMonth(), e.drawYear = e.selectedYear = o.getFullYear(), ("M" === n || "Y" === n) && this._notifyChange(e);
        },
        _restrictMinMax: function(e, t) {
            var n = this._getMinMaxDate(e, "min"), r = this._getMinMaxDate(e, "max"), i = n && n > t ? n : t;
            return r && i > r ? r : i;
        },
        _notifyChange: function(e) {
            var t = this._get(e, "onChangeMonthYear");
            t && t.apply(e.input ? e.input[0] : null, [ e.selectedYear, e.selectedMonth + 1, e ]);
        },
        _getNumberOfMonths: function(e) {
            var t = this._get(e, "numberOfMonths");
            return null == t ? [ 1, 1 ] : "number" == typeof t ? [ 1, t ] : t;
        },
        _getMinMaxDate: function(e, t) {
            return this._determineDate(e, this._get(e, t + "Date"), null);
        },
        _getDaysInMonth: function(e, t) {
            return 32 - this._daylightSavingAdjust(new Date(e, t, 32)).getDate();
        },
        _getFirstDayOfMonth: function(e, t) {
            return (new Date(e, t, 1)).getDay();
        },
        _canAdjustMonth: function(e, t, n, r) {
            var i = this._getNumberOfMonths(e), s = this._daylightSavingAdjust(new Date(n, r + (0 > t ? t : i[0] * i[1]), 1));
            return 0 > t && s.setDate(this._getDaysInMonth(s.getFullYear(), s.getMonth())), this._isInRange(e, s);
        },
        _isInRange: function(e, t) {
            var n, r, i = this._getMinMaxDate(e, "min"), s = this._getMinMaxDate(e, "max"), o = null, u = null, a = this._get(e, "yearRange");
            return a && (n = a.split(":"), r = (new Date).getFullYear(), o = parseInt(n[0], 10), u = parseInt(n[1], 10), n[0].match(/[+\-].*/) && (o += r), n[1].match(/[+\-].*/) && (u += r)), (!i || t.getTime() >= i.getTime()) && (!s || t.getTime() <= s.getTime()) && (!o || t.getFullYear() >= o) && (!u || u >= t.getFullYear());
        },
        _getFormatConfig: function(e) {
            var t = this._get(e, "shortYearCutoff");
            return t = "string" != typeof t ? t : (new Date).getFullYear() % 100 + parseInt(t, 10), {
                shortYearCutoff: t,
                dayNamesShort: this._get(e, "dayNamesShort"),
                dayNames: this._get(e, "dayNames"),
                monthNamesShort: this._get(e, "monthNamesShort"),
                monthNames: this._get(e, "monthNames")
            };
        },
        _formatDate: function(e, t, n, r) {
            t || (e.currentDay = e.selectedDay, e.currentMonth = e.selectedMonth, e.currentYear = e.selectedYear);
            var i = t ? "object" == typeof t ? t : this._daylightSavingAdjust(new Date(r, n, t)) : this._daylightSavingAdjust(new Date(e.currentYear, e.currentMonth, e.currentDay));
            return this.formatDate(this._get(e, "dateFormat"), i, this._getFormatConfig(e));
        }
    }), e.fn.datepicker = function(t) {
        if (!this.length) return this;
        e.datepicker.initialized || (e(document).mousedown(e.datepicker._checkExternalClick), e.datepicker.initialized = !0), 0 === e("#" + e.datepicker._mainDivId).length && e("body").append(e.datepicker.dpDiv);
        var n = Array.prototype.slice.call(arguments, 1);
        return "string" != typeof t || "isDisabled" !== t && "getDate" !== t && "widget" !== t ? "option" === t && 2 === arguments.length && "string" == typeof arguments[1] ? e.datepicker["_" + t + "Datepicker"].apply(e.datepicker, [ this[0] ].concat(n)) : this.each(function() {
            "string" == typeof t ? e.datepicker["_" + t + "Datepicker"].apply(e.datepicker, [ this ].concat(n)) : e.datepicker._attachDatepicker(this, t);
        }) : e.datepicker["_" + t + "Datepicker"].apply(e.datepicker, [ this[0] ].concat(n));
    }, e.datepicker = new n, e.datepicker.initialized = !1, e.datepicker.uuid = (new Date).getTime(), e.datepicker.version = "1.10.3";
})(jQuery);

(function(e) {
    var t = {
        buttons: !0,
        height: !0,
        maxHeight: !0,
        maxWidth: !0,
        minHeight: !0,
        minWidth: !0,
        width: !0
    }, n = {
        maxHeight: !0,
        maxWidth: !0,
        minHeight: !0,
        minWidth: !0
    };
    e.widget("ui.dialog", {
        version: "1.10.3",
        options: {
            appendTo: "body",
            autoOpen: !0,
            buttons: [],
            closeOnEscape: !0,
            closeText: "close",
            dialogClass: "",
            draggable: !0,
            hide: null,
            height: "auto",
            maxHeight: null,
            maxWidth: null,
            minHeight: 150,
            minWidth: 150,
            modal: !1,
            position: {
                my: "center",
                at: "center",
                of: window,
                collision: "fit",
                using: function(t) {
                    var n = e(this).css(t).offset().top;
                    0 > n && e(this).css("top", t.top - n);
                }
            },
            resizable: !0,
            show: null,
            title: null,
            width: 300,
            beforeClose: null,
            close: null,
            drag: null,
            dragStart: null,
            dragStop: null,
            focus: null,
            open: null,
            resize: null,
            resizeStart: null,
            resizeStop: null
        },
        _create: function() {
            this.originalCss = {
                display: this.element[0].style.display,
                width: this.element[0].style.width,
                minHeight: this.element[0].style.minHeight,
                maxHeight: this.element[0].style.maxHeight,
                height: this.element[0].style.height
            }, this.originalPosition = {
                parent: this.element.parent(),
                index: this.element.parent().children().index(this.element)
            }, this.originalTitle = this.element.attr("title"), this.options.title = this.options.title || this.originalTitle, this._createWrapper(), this.element.show().removeAttr("title").addClass("ui-dialog-content ui-widget-content").appendTo(this.uiDialog), this._createTitlebar(), this._createButtonPane(), this.options.draggable && e.fn.draggable && this._makeDraggable(), this.options.resizable && e.fn.resizable && this._makeResizable(), this._isOpen = !1;
        },
        _init: function() {
            this.options.autoOpen && this.open();
        },
        _appendTo: function() {
            var t = this.options.appendTo;
            return t && (t.jquery || t.nodeType) ? e(t) : this.document.find(t || "body").eq(0);
        },
        _destroy: function() {
            var e, t = this.originalPosition;
            this._destroyOverlay(), this.element.removeUniqueId().removeClass("ui-dialog-content ui-widget-content").css(this.originalCss).detach(), this.uiDialog.stop(!0, !0).remove(), this.originalTitle && this.element.attr("title", this.originalTitle), e = t.parent.children().eq(t.index), e.length && e[0] !== this.element[0] ? e.before(this.element) : t.parent.append(this.element);
        },
        widget: function() {
            return this.uiDialog;
        },
        disable: e.noop,
        enable: e.noop,
        close: function(t) {
            var n = this;
            this._isOpen && this._trigger("beforeClose", t) !== !1 && (this._isOpen = !1, this._destroyOverlay(), this.opener.filter(":focusable").focus().length || e(this.document[0].activeElement).blur(), this._hide(this.uiDialog, this.options.hide, function() {
                n._trigger("close", t);
            }));
        },
        isOpen: function() {
            return this._isOpen;
        },
        moveToTop: function() {
            this._moveToTop();
        },
        _moveToTop: function(e, t) {
            var n = !!this.uiDialog.nextAll(":visible").insertBefore(this.uiDialog).length;
            return n && !t && this._trigger("focus", e), n;
        },
        open: function() {
            var t = this;
            return this._isOpen ? (this._moveToTop() && this._focusTabbable(), undefined) : (this._isOpen = !0, this.opener = e(this.document[0].activeElement), this._size(), this._position(), this._createOverlay(), this._moveToTop(null, !0), this._show(this.uiDialog, this.options.show, function() {
                t._focusTabbable(), t._trigger("focus");
            }), this._trigger("open"), undefined);
        },
        _focusTabbable: function() {
            var e = this.element.find("[autofocus]");
            e.length || (e = this.element.find(":tabbable")), e.length || (e = this.uiDialogButtonPane.find(":tabbable")), e.length || (e = this.uiDialogTitlebarClose.filter(":tabbable")), e.length || (e = this.uiDialog), e.eq(0).focus();
        },
        _keepFocus: function(t) {
            function n() {
                var t = this.document[0].activeElement, n = this.uiDialog[0] === t || e.contains(this.uiDialog[0], t);
                n || this._focusTabbable();
            }
            t.preventDefault(), n.call(this), this._delay(n);
        },
        _createWrapper: function() {
            this.uiDialog = e("<div>").addClass("ui-dialog ui-widget ui-widget-content ui-corner-all ui-front " + this.options.dialogClass).hide().attr({
                tabIndex: -1,
                role: "dialog"
            }).appendTo(this._appendTo()), this._on(this.uiDialog, {
                keydown: function(t) {
                    if (this.options.closeOnEscape && !t.isDefaultPrevented() && t.keyCode && t.keyCode === e.ui.keyCode.ESCAPE) return t.preventDefault(), this.close(t), undefined;
                    if (t.keyCode === e.ui.keyCode.TAB) {
                        var n = this.uiDialog.find(":tabbable"), r = n.filter(":first"), i = n.filter(":last");
                        t.target !== i[0] && t.target !== this.uiDialog[0] || t.shiftKey ? t.target !== r[0] && t.target !== this.uiDialog[0] || !t.shiftKey || (i.focus(1), t.preventDefault()) : (r.focus(1), t.preventDefault());
                    }
                },
                mousedown: function(e) {
                    this._moveToTop(e) && this._focusTabbable();
                }
            }), this.element.find("[aria-describedby]").length || this.uiDialog.attr({
                "aria-describedby": this.element.uniqueId().attr("id")
            });
        },
        _createTitlebar: function() {
            var t;
            this.uiDialogTitlebar = e("<div>").addClass("ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix").prependTo(this.uiDialog), this._on(this.uiDialogTitlebar, {
                mousedown: function(t) {
                    e(t.target).closest(".ui-dialog-titlebar-close") || this.uiDialog.focus();
                }
            }), this.uiDialogTitlebarClose = e("<button></button>").button({
                label: this.options.closeText,
                icons: {
                    primary: "ui-icon-closethick"
                },
                text: !1
            }).addClass("ui-dialog-titlebar-close").appendTo(this.uiDialogTitlebar), this._on(this.uiDialogTitlebarClose, {
                click: function(e) {
                    e.preventDefault(), this.close(e);
                }
            }), t = e("<span>").uniqueId().addClass("ui-dialog-title").prependTo(this.uiDialogTitlebar), this._title(t), this.uiDialog.attr({
                "aria-labelledby": t.attr("id")
            });
        },
        _title: function(e) {
            this.options.title || e.html("&#160;"), e.text(this.options.title);
        },
        _createButtonPane: function() {
            this.uiDialogButtonPane = e("<div>").addClass("ui-dialog-buttonpane ui-widget-content ui-helper-clearfix"), this.uiButtonSet = e("<div>").addClass("ui-dialog-buttonset").appendTo(this.uiDialogButtonPane), this._createButtons();
        },
        _createButtons: function() {
            var t = this, n = this.options.buttons;
            return this.uiDialogButtonPane.remove(), this.uiButtonSet.empty(), e.isEmptyObject(n) || e.isArray(n) && !n.length ? (this.uiDialog.removeClass("ui-dialog-buttons"), undefined) : (e.each(n, function(n, r) {
                var i, s;
                r = e.isFunction(r) ? {
                    click: r,
                    text: n
                } : r, r = e.extend({
                    type: "button"
                }, r), i = r.click, r.click = function() {
                    i.apply(t.element[0], arguments);
                }, s = {
                    icons: r.icons,
                    text: r.showText
                }, delete r.icons, delete r.showText, e("<button></button>", r).button(s).appendTo(t.uiButtonSet);
            }), this.uiDialog.addClass("ui-dialog-buttons"), this.uiDialogButtonPane.appendTo(this.uiDialog), undefined);
        },
        _makeDraggable: function() {
            function t(e) {
                return {
                    position: e.position,
                    offset: e.offset
                };
            }
            var n = this, r = this.options;
            this.uiDialog.draggable({
                cancel: ".ui-dialog-content, .ui-dialog-titlebar-close",
                handle: ".ui-dialog-titlebar",
                containment: "document",
                start: function(r, s) {
                    e(this).addClass("ui-dialog-dragging"), n._blockFrames(), n._trigger("dragStart", r, t(s));
                },
                drag: function(e, r) {
                    n._trigger("drag", e, t(r));
                },
                stop: function(s, o) {
                    r.position = [ o.position.left - n.document.scrollLeft(), o.position.top - n.document.scrollTop() ], e(this).removeClass("ui-dialog-dragging"), n._unblockFrames(), n._trigger("dragStop", s, t(o));
                }
            });
        },
        _makeResizable: function() {
            function t(e) {
                return {
                    originalPosition: e.originalPosition,
                    originalSize: e.originalSize,
                    position: e.position,
                    size: e.size
                };
            }
            var n = this, r = this.options, i = r.resizable, s = this.uiDialog.css("position"), o = "string" == typeof i ? i : "n,e,s,w,se,sw,ne,nw";
            this.uiDialog.resizable({
                cancel: ".ui-dialog-content",
                containment: "document",
                alsoResize: this.element,
                maxWidth: r.maxWidth,
                maxHeight: r.maxHeight,
                minWidth: r.minWidth,
                minHeight: this._minHeight(),
                handles: o,
                start: function(r, i) {
                    e(this).addClass("ui-dialog-resizing"), n._blockFrames(), n._trigger("resizeStart", r, t(i));
                },
                resize: function(e, r) {
                    n._trigger("resize", e, t(r));
                },
                stop: function(i, s) {
                    r.height = e(this).height(), r.width = e(this).width(), e(this).removeClass("ui-dialog-resizing"), n._unblockFrames(), n._trigger("resizeStop", i, t(s));
                }
            }).css("position", s);
        },
        _minHeight: function() {
            var e = this.options;
            return "auto" === e.height ? e.minHeight : Math.min(e.minHeight, e.height);
        },
        _position: function() {
            var e = this.uiDialog.is(":visible");
            e || this.uiDialog.show(), this.uiDialog.position(this.options.position), e || this.uiDialog.hide();
        },
        _setOptions: function(r) {
            var s = this, o = !1, u = {};
            e.each(r, function(e, r) {
                s._setOption(e, r), e in t && (o = !0), e in n && (u[e] = r);
            }), o && (this._size(), this._position()), this.uiDialog.is(":data(ui-resizable)") && this.uiDialog.resizable("option", u);
        },
        _setOption: function(e, t) {
            var n, r, i = this.uiDialog;
            "dialogClass" === e && i.removeClass(this.options.dialogClass).addClass(t), "disabled" !== e && (this._super(e, t), "appendTo" === e && this.uiDialog.appendTo(this._appendTo()), "buttons" === e && this._createButtons(), "closeText" === e && this.uiDialogTitlebarClose.button({
                label: "" + t
            }), "draggable" === e && (n = i.is(":data(ui-draggable)"), n && !t && i.draggable("destroy"), !n && t && this._makeDraggable()), "position" === e && this._position(), "resizable" === e && (r = i.is(":data(ui-resizable)"), r && !t && i.resizable("destroy"), r && "string" == typeof t && i.resizable("option", "handles", t), r || t === !1 || this._makeResizable()), "title" === e && this._title(this.uiDialogTitlebar.find(".ui-dialog-title")));
        },
        _size: function() {
            var e, t, n, r = this.options;
            this.element.show().css({
                width: "auto",
                minHeight: 0,
                maxHeight: "none",
                height: 0
            }), r.minWidth > r.width && (r.width = r.minWidth), e = this.uiDialog.css({
                height: "auto",
                width: r.width
            }).outerHeight(), t = Math.max(0, r.minHeight - e), n = "number" == typeof r.maxHeight ? Math.max(0, r.maxHeight - e) : "none", "auto" === r.height ? this.element.css({
                minHeight: t,
                maxHeight: n,
                height: "auto"
            }) : this.element.height(Math.max(0, r.height - e)), this.uiDialog.is(":data(ui-resizable)") && this.uiDialog.resizable("option", "minHeight", this._minHeight());
        },
        _blockFrames: function() {
            this.iframeBlocks = this.document.find("iframe").map(function() {
                var t = e(this);
                return e("<div>").css({
                    position: "absolute",
                    width: t.outerWidth(),
                    height: t.outerHeight()
                }).appendTo(t.parent()).offset(t.offset())[0];
            });
        },
        _unblockFrames: function() {
            this.iframeBlocks && (this.iframeBlocks.remove(), delete this.iframeBlocks);
        },
        _allowInteraction: function(t) {
            return e(t.target).closest(".ui-dialog").length ? !0 : !!e(t.target).closest(".ui-datepicker").length;
        },
        _createOverlay: function() {
            if (this.options.modal) {
                var t = this, n = this.widgetFullName;
                e.ui.dialog.overlayInstances || this._delay(function() {
                    e.ui.dialog.overlayInstances && this.document.bind("focusin.dialog", function(r) {
                        t._allowInteraction(r) || (r.preventDefault(), e(".ui-dialog:visible:last .ui-dialog-content").data(n)._focusTabbable());
                    });
                }), this.overlay = e("<div>").addClass("ui-widget-overlay ui-front").appendTo(this._appendTo()), this._on(this.overlay, {
                    mousedown: "_keepFocus"
                }), e.ui.dialog.overlayInstances++;
            }
        },
        _destroyOverlay: function() {
            this.options.modal && this.overlay && (e.ui.dialog.overlayInstances--, e.ui.dialog.overlayInstances || this.document.unbind("focusin.dialog"), this.overlay.remove(), this.overlay = null);
        }
    }), e.ui.dialog.overlayInstances = 0, e.uiBackCompat !== !1 && e.widget("ui.dialog", e.ui.dialog, {
        _position: function() {
            var t, n = this.options.position, r = [], i = [ 0, 0 ];
            n ? (("string" == typeof n || "object" == typeof n && "0" in n) && (r = n.split ? n.split(" ") : [ n[0], n[1] ], 1 === r.length && (r[1] = r[0]), e.each([ "left", "top" ], function(e, t) {
                +r[e] === r[e] && (i[e] = r[e], r[e] = t);
            }), n = {
                my: r[0] + (0 > i[0] ? i[0] : "+" + i[0]) + " " + r[1] + (0 > i[1] ? i[1] : "+" + i[1]),
                at: r.join(" ")
            }), n = e.extend({}, e.ui.dialog.prototype.options.position, n)) : n = e.ui.dialog.prototype.options.position, t = this.uiDialog.is(":visible"), t || this.uiDialog.show(), this.uiDialog.position(n), t || this.uiDialog.hide();
        }
    });
})(jQuery);

(function(e) {
    e.widget("ui.menu", {
        version: "1.10.3",
        defaultElement: "<ul>",
        delay: 300,
        options: {
            icons: {
                submenu: "ui-icon-carat-1-e"
            },
            menus: "ul",
            position: {
                my: "left top",
                at: "right top"
            },
            role: "menu",
            blur: null,
            focus: null,
            select: null
        },
        _create: function() {
            this.activeMenu = this.element, this.mouseHandled = !1, this.element.uniqueId().addClass("ui-menu ui-widget ui-widget-content ui-corner-all").toggleClass("ui-menu-icons", !!this.element.find(".ui-icon").length).attr({
                role: this.options.role,
                tabIndex: 0
            }).bind("click" + this.eventNamespace, e.proxy(function(e) {
                this.options.disabled && e.preventDefault();
            }, this)), this.options.disabled && this.element.addClass("ui-state-disabled").attr("aria-disabled", "true"), this._on({
                "mousedown .ui-menu-item > a": function(e) {
                    e.preventDefault();
                },
                "click .ui-state-disabled > a": function(e) {
                    e.preventDefault();
                },
                "click .ui-menu-item:has(a)": function(n) {
                    var r = e(n.target).closest(".ui-menu-item");
                    !this.mouseHandled && r.not(".ui-state-disabled").length && (this.mouseHandled = !0, this.select(n), r.has(".ui-menu").length ? this.expand(n) : this.element.is(":focus") || (this.element.trigger("focus", [ !0 ]), this.active && 1 === this.active.parents(".ui-menu").length && clearTimeout(this.timer)));
                },
                "mouseenter .ui-menu-item": function(n) {
                    var r = e(n.currentTarget);
                    r.siblings().children(".ui-state-active").removeClass("ui-state-active"), this.focus(n, r);
                },
                mouseleave: "collapseAll",
                "mouseleave .ui-menu": "collapseAll",
                focus: function(e, t) {
                    var n = this.active || this.element.children(".ui-menu-item").eq(0);
                    t || this.focus(e, n);
                },
                blur: function(n) {
                    this._delay(function() {
                        e.contains(this.element[0], this.document[0].activeElement) || this.collapseAll(n);
                    });
                },
                keydown: "_keydown"
            }), this.refresh(), this._on(this.document, {
                click: function(n) {
                    e(n.target).closest(".ui-menu").length || this.collapseAll(n), this.mouseHandled = !1;
                }
            });
        },
        _destroy: function() {
            this.element.removeAttr("aria-activedescendant").find(".ui-menu").addBack().removeClass("ui-menu ui-widget ui-widget-content ui-corner-all ui-menu-icons").removeAttr("role").removeAttr("tabIndex").removeAttr("aria-labelledby").removeAttr("aria-expanded").removeAttr("aria-hidden").removeAttr("aria-disabled").removeUniqueId().show(), this.element.find(".ui-menu-item").removeClass("ui-menu-item").removeAttr("role").removeAttr("aria-disabled").children("a").removeUniqueId().removeClass("ui-corner-all ui-state-hover").removeAttr("tabIndex").removeAttr("role").removeAttr("aria-haspopup").children().each(function() {
                var n = e(this);
                n.data("ui-menu-submenu-carat") && n.remove();
            }), this.element.find(".ui-menu-divider").removeClass("ui-menu-divider ui-widget-content");
        },
        _keydown: function(n) {
            function r(e) {
                return e.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, "\\$&");
            }
            var i, s, o, u, a, f = !0;
            switch (n.keyCode) {
              case e.ui.keyCode.PAGE_UP:
                this.previousPage(n);
                break;
              case e.ui.keyCode.PAGE_DOWN:
                this.nextPage(n);
                break;
              case e.ui.keyCode.HOME:
                this._move("first", "first", n);
                break;
              case e.ui.keyCode.END:
                this._move("last", "last", n);
                break;
              case e.ui.keyCode.UP:
                this.previous(n);
                break;
              case e.ui.keyCode.DOWN:
                this.next(n);
                break;
              case e.ui.keyCode.LEFT:
                this.collapse(n);
                break;
              case e.ui.keyCode.RIGHT:
                this.active && !this.active.is(".ui-state-disabled") && this.expand(n);
                break;
              case e.ui.keyCode.ENTER:
              case e.ui.keyCode.SPACE:
                this._activate(n);
                break;
              case e.ui.keyCode.ESCAPE:
                this.collapse(n);
                break;
              default:
                f = !1, s = this.previousFilter || "", o = String.fromCharCode(n.keyCode), u = !1, clearTimeout(this.filterTimer), o === s ? u = !0 : o = s + o, a = RegExp("^" + r(o), "i"), i = this.activeMenu.children(".ui-menu-item").filter(function() {
                    return a.test(e(this).children("a").text());
                }), i = u && -1 !== i.index(this.active.next()) ? this.active.nextAll(".ui-menu-item") : i, i.length || (o = String.fromCharCode(n.keyCode), a = RegExp("^" + r(o), "i"), i = this.activeMenu.children(".ui-menu-item").filter(function() {
                    return a.test(e(this).children("a").text());
                })), i.length ? (this.focus(n, i), i.length > 1 ? (this.previousFilter = o, this.filterTimer = this._delay(function() {
                    delete this.previousFilter;
                }, 1e3)) : delete this.previousFilter) : delete this.previousFilter;
            }
            f && n.preventDefault();
        },
        _activate: function(e) {
            this.active.is(".ui-state-disabled") || (this.active.children("a[aria-haspopup='true']").length ? this.expand(e) : this.select(e));
        },
        refresh: function() {
            var n, r = this.options.icons.submenu, i = this.element.find(this.options.menus);
            i.filter(":not(.ui-menu)").addClass("ui-menu ui-widget ui-widget-content ui-corner-all").hide().attr({
                role: this.options.role,
                "aria-hidden": "true",
                "aria-expanded": "false"
            }).each(function() {
                var n = e(this), i = n.prev("a"), s = e("<span>").addClass("ui-menu-icon ui-icon " + r).data("ui-menu-submenu-carat", !0);
                i.attr("aria-haspopup", "true").prepend(s), n.attr("aria-labelledby", i.attr("id"));
            }), n = i.add(this.element), n.children(":not(.ui-menu-item):has(a)").addClass("ui-menu-item").attr("role", "presentation").children("a").uniqueId().addClass("ui-corner-all").attr({
                tabIndex: -1,
                role: this._itemRole()
            }), n.children(":not(.ui-menu-item)").each(function() {
                var n = e(this);
                /[^\-\u2014\u2013\s]/.test(n.text()) || n.addClass("ui-widget-content ui-menu-divider");
            }), n.children(".ui-state-disabled").attr("aria-disabled", "true"), this.active && !e.contains(this.element[0], this.active[0]) && this.blur();
        },
        _itemRole: function() {
            return {
                menu: "menuitem",
                listbox: "option"
            }[this.options.role];
        },
        _setOption: function(e, t) {
            "icons" === e && this.element.find(".ui-menu-icon").removeClass(this.options.icons.submenu).addClass(t.submenu), this._super(e, t);
        },
        focus: function(e, t) {
            var n, r;
            this.blur(e, e && "focus" === e.type), this._scrollIntoView(t), this.active = t.first(), r = this.active.children("a").addClass("ui-state-focus"), this.options.role && this.element.attr("aria-activedescendant", r.attr("id")), this.active.parent().closest(".ui-menu-item").children("a:first").addClass("ui-state-active"), e && "keydown" === e.type ? this._close() : this.timer = this._delay(function() {
                this._close();
            }, this.delay), n = t.children(".ui-menu"), n.length && /^mouse/.test(e.type) && this._startOpening(n), this.activeMenu = t.parent(), this._trigger("focus", e, {
                item: t
            });
        },
        _scrollIntoView: function(n) {
            var r, i, s, o, u, a;
            this._hasScroll() && (r = parseFloat(e.css(this.activeMenu[0], "borderTopWidth")) || 0, i = parseFloat(e.css(this.activeMenu[0], "paddingTop")) || 0, s = n.offset().top - this.activeMenu.offset().top - r - i, o = this.activeMenu.scrollTop(), u = this.activeMenu.height(), a = n.height(), 0 > s ? this.activeMenu.scrollTop(o + s) : s + a > u && this.activeMenu.scrollTop(o + s - u + a));
        },
        blur: function(e, t) {
            t || clearTimeout(this.timer), this.active && (this.active.children("a").removeClass("ui-state-focus"), this.active = null, this._trigger("blur", e, {
                item: this.active
            }));
        },
        _startOpening: function(e) {
            clearTimeout(this.timer), "true" === e.attr("aria-hidden") && (this.timer = this._delay(function() {
                this._close(), this._open(e);
            }, this.delay));
        },
        _open: function(n) {
            var r = e.extend({
                of: this.active
            }, this.options.position);
            clearTimeout(this.timer), this.element.find(".ui-menu").not(n.parents(".ui-menu")).hide().attr("aria-hidden", "true"), n.show().removeAttr("aria-hidden").attr("aria-expanded", "true").position(r);
        },
        collapseAll: function(n, r) {
            clearTimeout(this.timer), this.timer = this._delay(function() {
                var s = r ? this.element : e(n && n.target).closest(this.element.find(".ui-menu"));
                s.length || (s = this.element), this._close(s), this.blur(n), this.activeMenu = s;
            }, this.delay);
        },
        _close: function(e) {
            e || (e = this.active ? this.active.parent() : this.element), e.find(".ui-menu").hide().attr("aria-hidden", "true").attr("aria-expanded", "false").end().find("a.ui-state-active").removeClass("ui-state-active");
        },
        collapse: function(e) {
            var t = this.active && this.active.parent().closest(".ui-menu-item", this.element);
            t && t.length && (this._close(), this.focus(e, t));
        },
        expand: function(e) {
            var t = this.active && this.active.children(".ui-menu ").children(".ui-menu-item").first();
            t && t.length && (this._open(t.parent()), this._delay(function() {
                this.focus(e, t);
            }));
        },
        next: function(e) {
            this._move("next", "first", e);
        },
        previous: function(e) {
            this._move("prev", "last", e);
        },
        isFirstItem: function() {
            return this.active && !this.active.prevAll(".ui-menu-item").length;
        },
        isLastItem: function() {
            return this.active && !this.active.nextAll(".ui-menu-item").length;
        },
        _move: function(e, t, n) {
            var r;
            this.active && (r = "first" === e || "last" === e ? this.active["first" === e ? "prevAll" : "nextAll"](".ui-menu-item").eq(-1) : this.active[e + "All"](".ui-menu-item").eq(0)), r && r.length && this.active || (r = this.activeMenu.children(".ui-menu-item")[t]()), this.focus(n, r);
        },
        nextPage: function(n) {
            var r, i, s;
            return this.active ? (this.isLastItem() || (this._hasScroll() ? (i = this.active.offset().top, s = this.element.height(), this.active.nextAll(".ui-menu-item").each(function() {
                return r = e(this), 0 > r.offset().top - i - s;
            }), this.focus(n, r)) : this.focus(n, this.activeMenu.children(".ui-menu-item")[this.active ? "last" : "first"]())), undefined) : (this.next(n), undefined);
        },
        previousPage: function(n) {
            var r, i, s;
            return this.active ? (this.isFirstItem() || (this._hasScroll() ? (i = this.active.offset().top, s = this.element.height(), this.active.prevAll(".ui-menu-item").each(function() {
                return r = e(this), r.offset().top - i + s > 0;
            }), this.focus(n, r)) : this.focus(n, this.activeMenu.children(".ui-menu-item").first())), undefined) : (this.next(n), undefined);
        },
        _hasScroll: function() {
            return this.element.outerHeight() < this.element.prop("scrollHeight");
        },
        select: function(n) {
            this.active = this.active || e(n.target).closest(".ui-menu-item");
            var r = {
                item: this.active
            };
            this.active.has(".ui-menu").length || this.collapseAll(n, !0), this._trigger("select", n, r);
        }
    });
})(jQuery);

(function(e, t) {
    e.widget("ui.progressbar", {
        version: "1.10.3",
        options: {
            max: 100,
            value: 0,
            change: null,
            complete: null
        },
        min: 0,
        _create: function() {
            this.oldValue = this.options.value = this._constrainedValue(), this.element.addClass("ui-progressbar ui-widget ui-widget-content ui-corner-all").attr({
                role: "progressbar",
                "aria-valuemin": this.min
            }), this.valueDiv = e("<div class='ui-progressbar-value ui-widget-header ui-corner-left'></div>").appendTo(this.element), this._refreshValue();
        },
        _destroy: function() {
            this.element.removeClass("ui-progressbar ui-widget ui-widget-content ui-corner-all").removeAttr("role").removeAttr("aria-valuemin").removeAttr("aria-valuemax").removeAttr("aria-valuenow"), this.valueDiv.remove();
        },
        value: function(e) {
            return e === t ? this.options.value : (this.options.value = this._constrainedValue(e), this._refreshValue(), t);
        },
        _constrainedValue: function(e) {
            return e === t && (e = this.options.value), this.indeterminate = e === !1, "number" != typeof e && (e = 0), this.indeterminate ? !1 : Math.min(this.options.max, Math.max(this.min, e));
        },
        _setOptions: function(e) {
            var t = e.value;
            delete e.value, this._super(e), this.options.value = this._constrainedValue(t), this._refreshValue();
        },
        _setOption: function(e, t) {
            "max" === e && (t = Math.max(this.min, t)), this._super(e, t);
        },
        _percentage: function() {
            return this.indeterminate ? 100 : 100 * (this.options.value - this.min) / (this.options.max - this.min);
        },
        _refreshValue: function() {
            var t = this.options.value, n = this._percentage();
            this.valueDiv.toggle(this.indeterminate || t > this.min).toggleClass("ui-corner-right", t === this.options.max).width(n.toFixed(0) + "%"), this.element.toggleClass("ui-progressbar-indeterminate", this.indeterminate), this.indeterminate ? (this.element.removeAttr("aria-valuenow"), this.overlayDiv || (this.overlayDiv = e("<div class='ui-progressbar-overlay'></div>").appendTo(this.valueDiv))) : (this.element.attr({
                "aria-valuemax": this.options.max,
                "aria-valuenow": t
            }), this.overlayDiv && (this.overlayDiv.remove(), this.overlayDiv = null)), this.oldValue !== t && (this.oldValue = t, this._trigger("change")), t === this.options.max && this._trigger("complete");
        }
    });
})(jQuery);

(function(e) {
    var t = 5;
    e.widget("ui.slider", e.ui.mouse, {
        version: "1.10.3",
        widgetEventPrefix: "slide",
        options: {
            animate: !1,
            distance: 0,
            max: 100,
            min: 0,
            orientation: "horizontal",
            range: !1,
            step: 1,
            value: 0,
            values: null,
            change: null,
            slide: null,
            start: null,
            stop: null
        },
        _create: function() {
            this._keySliding = !1, this._mouseSliding = !1, this._animateOff = !0, this._handleIndex = null, this._detectOrientation(), this._mouseInit(), this.element.addClass("ui-slider ui-slider-" + this.orientation + " ui-widget" + " ui-widget-content" + " ui-corner-all"), this._refresh(), this._setOption("disabled", this.options.disabled), this._animateOff = !1;
        },
        _refresh: function() {
            this._createRange(), this._createHandles(), this._setupEvents(), this._refreshValue();
        },
        _createHandles: function() {
            var t, n, r = this.options, i = this.element.find(".ui-slider-handle").addClass("ui-state-default ui-corner-all"), s = "<a class='ui-slider-handle ui-state-default ui-corner-all' href='#'></a>", o = [];
            for (n = r.values && r.values.length || 1, i.length > n && (i.slice(n).remove(), i = i.slice(0, n)), t = i.length; n > t; t++) o.push(s);
            this.handles = i.add(e(o.join("")).appendTo(this.element)), this.handle = this.handles.eq(0), this.handles.each(function(t) {
                e(this).data("ui-slider-handle-index", t);
            });
        },
        _createRange: function() {
            var t = this.options, n = "";
            t.range ? (t.range === !0 && (t.values ? t.values.length && 2 !== t.values.length ? t.values = [ t.values[0], t.values[0] ] : e.isArray(t.values) && (t.values = t.values.slice(0)) : t.values = [ this._valueMin(), this._valueMin() ]), this.range && this.range.length ? this.range.removeClass("ui-slider-range-min ui-slider-range-max").css({
                left: "",
                bottom: ""
            }) : (this.range = e("<div></div>").appendTo(this.element), n = "ui-slider-range ui-widget-header ui-corner-all"), this.range.addClass(n + ("min" === t.range || "max" === t.range ? " ui-slider-range-" + t.range : ""))) : this.range = e([]);
        },
        _setupEvents: function() {
            var e = this.handles.add(this.range).filter("a");
            this._off(e), this._on(e, this._handleEvents), this._hoverable(e), this._focusable(e);
        },
        _destroy: function() {
            this.handles.remove(), this.range.remove(), this.element.removeClass("ui-slider ui-slider-horizontal ui-slider-vertical ui-widget ui-widget-content ui-corner-all"), this._mouseDestroy();
        },
        _mouseCapture: function(t) {
            var n, r, i, s, o, u, a, f, l = this, c = this.options;
            return c.disabled ? !1 : (this.elementSize = {
                width: this.element.outerWidth(),
                height: this.element.outerHeight()
            }, this.elementOffset = this.element.offset(), n = {
                x: t.pageX,
                y: t.pageY
            }, r = this._normValueFromMouse(n), i = this._valueMax() - this._valueMin() + 1, this.handles.each(function(t) {
                var n = Math.abs(r - l.values(t));
                (i > n || i === n && (t === l._lastChangedValue || l.values(t) === c.min)) && (i = n, s = e(this), o = t);
            }), u = this._start(t, o), u === !1 ? !1 : (this._mouseSliding = !0, this._handleIndex = o, s.addClass("ui-state-active").focus(), a = s.offset(), f = !e(t.target).parents().addBack().is(".ui-slider-handle"), this._clickOffset = f ? {
                left: 0,
                top: 0
            } : {
                left: t.pageX - a.left - s.width() / 2,
                top: t.pageY - a.top - s.height() / 2 - (parseInt(s.css("borderTopWidth"), 10) || 0) - (parseInt(s.css("borderBottomWidth"), 10) || 0) + (parseInt(s.css("marginTop"), 10) || 0)
            }, this.handles.hasClass("ui-state-hover") || this._slide(t, o, r), this._animateOff = !0, !0));
        },
        _mouseStart: function() {
            return !0;
        },
        _mouseDrag: function(e) {
            var t = {
                x: e.pageX,
                y: e.pageY
            }, n = this._normValueFromMouse(t);
            return this._slide(e, this._handleIndex, n), !1;
        },
        _mouseStop: function(e) {
            return this.handles.removeClass("ui-state-active"), this._mouseSliding = !1, this._stop(e, this._handleIndex), this._change(e, this._handleIndex), this._handleIndex = null, this._clickOffset = null, this._animateOff = !1, !1;
        },
        _detectOrientation: function() {
            this.orientation = "vertical" === this.options.orientation ? "vertical" : "horizontal";
        },
        _normValueFromMouse: function(e) {
            var t, n, r, i, s;
            return "horizontal" === this.orientation ? (t = this.elementSize.width, n = e.x - this.elementOffset.left - (this._clickOffset ? this._clickOffset.left : 0)) : (t = this.elementSize.height, n = e.y - this.elementOffset.top - (this._clickOffset ? this._clickOffset.top : 0)), r = n / t, r > 1 && (r = 1), 0 > r && (r = 0), "vertical" === this.orientation && (r = 1 - r), i = this._valueMax() - this._valueMin(), s = this._valueMin() + r * i, this._trimAlignValue(s);
        },
        _start: function(e, t) {
            var n = {
                handle: this.handles[t],
                value: this.value()
            };
            return this.options.values && this.options.values.length && (n.value = this.values(t), n.values = this.values()), this._trigger("start", e, n);
        },
        _slide: function(e, t, n) {
            var r, i, s;
            this.options.values && this.options.values.length ? (r = this.values(t ? 0 : 1), 2 === this.options.values.length && this.options.range === !0 && (0 === t && n > r || 1 === t && r > n) && (n = r), n !== this.values(t) && (i = this.values(), i[t] = n, s = this._trigger("slide", e, {
                handle: this.handles[t],
                value: n,
                values: i
            }), r = this.values(t ? 0 : 1), s !== !1 && this.values(t, n, !0))) : n !== this.value() && (s = this._trigger("slide", e, {
                handle: this.handles[t],
                value: n
            }), s !== !1 && this.value(n));
        },
        _stop: function(e, t) {
            var n = {
                handle: this.handles[t],
                value: this.value()
            };
            this.options.values && this.options.values.length && (n.value = this.values(t), n.values = this.values()), this._trigger("stop", e, n);
        },
        _change: function(e, t) {
            if (!this._keySliding && !this._mouseSliding) {
                var n = {
                    handle: this.handles[t],
                    value: this.value()
                };
                this.options.values && this.options.values.length && (n.value = this.values(t), n.values = this.values()), this._lastChangedValue = t, this._trigger("change", e, n);
            }
        },
        value: function(e) {
            return arguments.length ? (this.options.value = this._trimAlignValue(e), this._refreshValue(), this._change(null, 0), undefined) : this._value();
        },
        values: function(t, n) {
            var r, i, s;
            if (arguments.length > 1) return this.options.values[t] = this._trimAlignValue(n), this._refreshValue(), this._change(null, t), undefined;
            if (!arguments.length) return this._values();
            if (!e.isArray(arguments[0])) return this.options.values && this.options.values.length ? this._values(t) : this.value();
            for (r = this.options.values, i = arguments[0], s = 0; r.length > s; s += 1) r[s] = this._trimAlignValue(i[s]), this._change(null, s);
            this._refreshValue();
        },
        _setOption: function(t, n) {
            var r, i = 0;
            switch ("range" === t && this.options.range === !0 && ("min" === n ? (this.options.value = this._values(0), this.options.values = null) : "max" === n && (this.options.value = this._values(this.options.values.length - 1), this.options.values = null)), e.isArray(this.options.values) && (i = this.options.values.length), e.Widget.prototype._setOption.apply(this, arguments), t) {
              case "orientation":
                this._detectOrientation(), this.element.removeClass("ui-slider-horizontal ui-slider-vertical").addClass("ui-slider-" + this.orientation), this._refreshValue();
                break;
              case "value":
                this._animateOff = !0, this._refreshValue(), this._change(null, 0), this._animateOff = !1;
                break;
              case "values":
                for (this._animateOff = !0, this._refreshValue(), r = 0; i > r; r += 1) this._change(null, r);
                this._animateOff = !1;
                break;
              case "min":
              case "max":
                this._animateOff = !0, this._refreshValue(), this._animateOff = !1;
                break;
              case "range":
                this._animateOff = !0, this._refresh(), this._animateOff = !1;
            }
        },
        _value: function() {
            var e = this.options.value;
            return e = this._trimAlignValue(e);
        },
        _values: function(e) {
            var t, n, r;
            if (arguments.length) return t = this.options.values[e], t = this._trimAlignValue(t);
            if (this.options.values && this.options.values.length) {
                for (n = this.options.values.slice(), r = 0; n.length > r; r += 1) n[r] = this._trimAlignValue(n[r]);
                return n;
            }
            return [];
        },
        _trimAlignValue: function(e) {
            if (this._valueMin() >= e) return this._valueMin();
            if (e >= this._valueMax()) return this._valueMax();
            var t = this.options.step > 0 ? this.options.step : 1, n = (e - this._valueMin()) % t, r = e - n;
            return 2 * Math.abs(n) >= t && (r += n > 0 ? t : -t), parseFloat(r.toFixed(5));
        },
        _valueMin: function() {
            return this.options.min;
        },
        _valueMax: function() {
            return this.options.max;
        },
        _refreshValue: function() {
            var t, n, r, i, s, o = this.options.range, u = this.options, a = this, f = this._animateOff ? !1 : u.animate, l = {};
            this.options.values && this.options.values.length ? this.handles.each(function(r) {
                n = 100 * ((a.values(r) - a._valueMin()) / (a._valueMax() - a._valueMin())), l["horizontal" === a.orientation ? "left" : "bottom"] = n + "%", e(this).stop(1, 1)[f ? "animate" : "css"](l, u.animate), a.options.range === !0 && ("horizontal" === a.orientation ? (0 === r && a.range.stop(1, 1)[f ? "animate" : "css"]({
                    left: n + "%"
                }, u.animate), 1 === r && a.range[f ? "animate" : "css"]({
                    width: n - t + "%"
                }, {
                    queue: !1,
                    duration: u.animate
                })) : (0 === r && a.range.stop(1, 1)[f ? "animate" : "css"]({
                    bottom: n + "%"
                }, u.animate), 1 === r && a.range[f ? "animate" : "css"]({
                    height: n - t + "%"
                }, {
                    queue: !1,
                    duration: u.animate
                }))), t = n;
            }) : (r = this.value(), i = this._valueMin(), s = this._valueMax(), n = s !== i ? 100 * ((r - i) / (s - i)) : 0, l["horizontal" === this.orientation ? "left" : "bottom"] = n + "%", this.handle.stop(1, 1)[f ? "animate" : "css"](l, u.animate), "min" === o && "horizontal" === this.orientation && this.range.stop(1, 1)[f ? "animate" : "css"]({
                width: n + "%"
            }, u.animate), "max" === o && "horizontal" === this.orientation && this.range[f ? "animate" : "css"]({
                width: 100 - n + "%"
            }, {
                queue: !1,
                duration: u.animate
            }), "min" === o && "vertical" === this.orientation && this.range.stop(1, 1)[f ? "animate" : "css"]({
                height: n + "%"
            }, u.animate), "max" === o && "vertical" === this.orientation && this.range[f ? "animate" : "css"]({
                height: 100 - n + "%"
            }, {
                queue: !1,
                duration: u.animate
            }));
        },
        _handleEvents: {
            keydown: function(n) {
                var r, i, s, o, u = e(n.target).data("ui-slider-handle-index");
                switch (n.keyCode) {
                  case e.ui.keyCode.HOME:
                  case e.ui.keyCode.END:
                  case e.ui.keyCode.PAGE_UP:
                  case e.ui.keyCode.PAGE_DOWN:
                  case e.ui.keyCode.UP:
                  case e.ui.keyCode.RIGHT:
                  case e.ui.keyCode.DOWN:
                  case e.ui.keyCode.LEFT:
                    if (n.preventDefault(), !this._keySliding && (this._keySliding = !0, e(n.target).addClass("ui-state-active"), r = this._start(n, u), r === !1)) return;
                }
                switch (o = this.options.step, i = s = this.options.values && this.options.values.length ? this.values(u) : this.value(), n.keyCode) {
                  case e.ui.keyCode.HOME:
                    s = this._valueMin();
                    break;
                  case e.ui.keyCode.END:
                    s = this._valueMax();
                    break;
                  case e.ui.keyCode.PAGE_UP:
                    s = this._trimAlignValue(i + (this._valueMax() - this._valueMin()) / t);
                    break;
                  case e.ui.keyCode.PAGE_DOWN:
                    s = this._trimAlignValue(i - (this._valueMax() - this._valueMin()) / t);
                    break;
                  case e.ui.keyCode.UP:
                  case e.ui.keyCode.RIGHT:
                    if (i === this._valueMax()) return;
                    s = this._trimAlignValue(i + o);
                    break;
                  case e.ui.keyCode.DOWN:
                  case e.ui.keyCode.LEFT:
                    if (i === this._valueMin()) return;
                    s = this._trimAlignValue(i - o);
                }
                this._slide(n, u, s);
            },
            click: function(e) {
                e.preventDefault();
            },
            keyup: function(t) {
                var n = e(t.target).data("ui-slider-handle-index");
                this._keySliding && (this._keySliding = !1, this._stop(t, n), this._change(t, n), e(t.target).removeClass("ui-state-active"));
            }
        }
    });
})(jQuery);

(function(e) {
    function t(e) {
        return function() {
            var t = this.element.val();
            e.apply(this, arguments), this._refresh(), t !== this.element.val() && this._trigger("change");
        };
    }
    e.widget("ui.spinner", {
        version: "1.10.3",
        defaultElement: "<input>",
        widgetEventPrefix: "spin",
        options: {
            culture: null,
            icons: {
                down: "ui-icon-triangle-1-s",
                up: "ui-icon-triangle-1-n"
            },
            incremental: !0,
            max: null,
            min: null,
            numberFormat: null,
            page: 10,
            step: 1,
            change: null,
            spin: null,
            start: null,
            stop: null
        },
        _create: function() {
            this._setOption("max", this.options.max), this._setOption("min", this.options.min), this._setOption("step", this.options.step), this._value(this.element.val(), !0), this._draw(), this._on(this._events), this._refresh(), this._on(this.window, {
                beforeunload: function() {
                    this.element.removeAttr("autocomplete");
                }
            });
        },
        _getCreateOptions: function() {
            var t = {}, n = this.element;
            return e.each([ "min", "max", "step" ], function(e, r) {
                var s = n.attr(r);
                void 0 !== s && s.length && (t[r] = s);
            }), t;
        },
        _events: {
            keydown: function(e) {
                this._start(e) && this._keydown(e) && e.preventDefault();
            },
            keyup: "_stop",
            focus: function() {
                this.previous = this.element.val();
            },
            blur: function(e) {
                return this.cancelBlur ? (delete this.cancelBlur, void 0) : (this._stop(), this._refresh(), this.previous !== this.element.val() && this._trigger("change", e), void 0);
            },
            mousewheel: function(e, t) {
                if (t) {
                    if (!this.spinning && !this._start(e)) return !1;
                    this._spin((t > 0 ? 1 : -1) * this.options.step, e), clearTimeout(this.mousewheelTimer), this.mousewheelTimer = this._delay(function() {
                        this.spinning && this._stop(e);
                    }, 100), e.preventDefault();
                }
            },
            "mousedown .ui-spinner-button": function(t) {
                function n() {
                    var e = this.element[0] === this.document[0].activeElement;
                    e || (this.element.focus(), this.previous = r, this._delay(function() {
                        this.previous = r;
                    }));
                }
                var r;
                r = this.element[0] === this.document[0].activeElement ? this.previous : this.element.val(), t.preventDefault(), n.call(this), this.cancelBlur = !0, this._delay(function() {
                    delete this.cancelBlur, n.call(this);
                }), this._start(t) !== !1 && this._repeat(null, e(t.currentTarget).hasClass("ui-spinner-up") ? 1 : -1, t);
            },
            "mouseup .ui-spinner-button": "_stop",
            "mouseenter .ui-spinner-button": function(t) {
                return e(t.currentTarget).hasClass("ui-state-active") ? this._start(t) === !1 ? !1 : (this._repeat(null, e(t.currentTarget).hasClass("ui-spinner-up") ? 1 : -1, t), void 0) : void 0;
            },
            "mouseleave .ui-spinner-button": "_stop"
        },
        _draw: function() {
            var e = this.uiSpinner = this.element.addClass("ui-spinner-input").attr("autocomplete", "off").wrap(this._uiSpinnerHtml()).parent().append(this._buttonHtml());
            this.element.attr("role", "spinbutton"), this.buttons = e.find(".ui-spinner-button").attr("tabIndex", -1).button().removeClass("ui-corner-all"), this.buttons.height() > Math.ceil(.5 * e.height()) && e.height() > 0 && e.height(e.height()), this.options.disabled && this.disable();
        },
        _keydown: function(t) {
            var n = this.options, r = e.ui.keyCode;
            switch (t.keyCode) {
              case r.UP:
                return this._repeat(null, 1, t), !0;
              case r.DOWN:
                return this._repeat(null, -1, t), !0;
              case r.PAGE_UP:
                return this._repeat(null, n.page, t), !0;
              case r.PAGE_DOWN:
                return this._repeat(null, -n.page, t), !0;
            }
            return !1;
        },
        _uiSpinnerHtml: function() {
            return "<span class='ui-spinner ui-widget ui-widget-content ui-corner-all'></span>";
        },
        _buttonHtml: function() {
            return "<a class='ui-spinner-button ui-spinner-up ui-corner-tr'><span class='ui-icon " + this.options.icons.up + "'>&#9650;</span>" + "</a>" + "<a class='ui-spinner-button ui-spinner-down ui-corner-br'>" + "<span class='ui-icon " + this.options.icons.down + "'>&#9660;</span>" + "</a>";
        },
        _start: function(e) {
            return this.spinning || this._trigger("start", e) !== !1 ? (this.counter || (this.counter = 1), this.spinning = !0, !0) : !1;
        },
        _repeat: function(e, t, n) {
            e = e || 500, clearTimeout(this.timer), this.timer = this._delay(function() {
                this._repeat(40, t, n);
            }, e), this._spin(t * this.options.step, n);
        },
        _spin: function(e, t) {
            var n = this.value() || 0;
            this.counter || (this.counter = 1), n = this._adjustValue(n + e * this._increment(this.counter)), this.spinning && this._trigger("spin", t, {
                value: n
            }) === !1 || (this._value(n), this.counter++);
        },
        _increment: function(t) {
            var n = this.options.incremental;
            return n ? e.isFunction(n) ? n(t) : Math.floor(t * t * t / 5e4 - t * t / 500 + 17 * t / 200 + 1) : 1;
        },
        _precision: function() {
            var e = this._precisionOf(this.options.step);
            return null !== this.options.min && (e = Math.max(e, this._precisionOf(this.options.min))), e;
        },
        _precisionOf: function(e) {
            var t = "" + e, n = t.indexOf(".");
            return -1 === n ? 0 : t.length - n - 1;
        },
        _adjustValue: function(e) {
            var t, n, r = this.options;
            return t = null !== r.min ? r.min : 0, n = e - t, n = Math.round(n / r.step) * r.step, e = t + n, e = parseFloat(e.toFixed(this._precision())), null !== r.max && e > r.max ? r.max : null !== r.min && r.min > e ? r.min : e;
        },
        _stop: function(e) {
            this.spinning && (clearTimeout(this.timer), clearTimeout(this.mousewheelTimer), this.counter = 0, this.spinning = !1, this._trigger("stop", e));
        },
        _setOption: function(e, t) {
            if ("culture" === e || "numberFormat" === e) {
                var n = this._parse(this.element.val());
                return this.options[e] = t, this.element.val(this._format(n)), void 0;
            }
            ("max" === e || "min" === e || "step" === e) && "string" == typeof t && (t = this._parse(t)), "icons" === e && (this.buttons.first().find(".ui-icon").removeClass(this.options.icons.up).addClass(t.up), this.buttons.last().find(".ui-icon").removeClass(this.options.icons.down).addClass(t.down)), this._super(e, t), "disabled" === e && (t ? (this.element.prop("disabled", !0), this.buttons.button("disable")) : (this.element.prop("disabled", !1), this.buttons.button("enable")));
        },
        _setOptions: t(function(e) {
            this._super(e), this._value(this.element.val());
        }),
        _parse: function(e) {
            return "string" == typeof e && "" !== e && (e = window.Globalize && this.options.numberFormat ? Globalize.parseFloat(e, 10, this.options.culture) : +e), "" === e || isNaN(e) ? null : e;
        },
        _format: function(e) {
            return "" === e ? "" : window.Globalize && this.options.numberFormat ? Globalize.format(e, this.options.numberFormat, this.options.culture) : e;
        },
        _refresh: function() {
            this.element.attr({
                "aria-valuemin": this.options.min,
                "aria-valuemax": this.options.max,
                "aria-valuenow": this._parse(this.element.val())
            });
        },
        _value: function(e, t) {
            var n;
            "" !== e && (n = this._parse(e), null !== n && (t || (n = this._adjustValue(n)), e = this._format(n))), this.element.val(e), this._refresh();
        },
        _destroy: function() {
            this.element.removeClass("ui-spinner-input").prop("disabled", !1).removeAttr("autocomplete").removeAttr("role").removeAttr("aria-valuemin").removeAttr("aria-valuemax").removeAttr("aria-valuenow"), this.uiSpinner.replaceWith(this.element);
        },
        stepUp: t(function(e) {
            this._stepUp(e);
        }),
        _stepUp: function(e) {
            this._start() && (this._spin((e || 1) * this.options.step), this._stop());
        },
        stepDown: t(function(e) {
            this._stepDown(e);
        }),
        _stepDown: function(e) {
            this._start() && (this._spin((e || 1) * -this.options.step), this._stop());
        },
        pageUp: t(function(e) {
            this._stepUp((e || 1) * this.options.page);
        }),
        pageDown: t(function(e) {
            this._stepDown((e || 1) * this.options.page);
        }),
        value: function(e) {
            return arguments.length ? (t(this._value).call(this, e), void 0) : this._parse(this.element.val());
        },
        widget: function() {
            return this.uiSpinner;
        }
    });
})(jQuery);

(function(e, t) {
    function n() {
        return ++i;
    }
    function r(e) {
        return e.hash.length > 1 && decodeURIComponent(e.href.replace(s, "")) === decodeURIComponent(location.href.replace(s, ""));
    }
    var i = 0, s = /#.*$/;
    e.widget("ui.tabs", {
        version: "1.10.3",
        delay: 300,
        options: {
            active: null,
            collapsible: !1,
            event: "click",
            heightStyle: "content",
            hide: null,
            show: null,
            activate: null,
            beforeActivate: null,
            beforeLoad: null,
            load: null
        },
        _create: function() {
            var t = this, n = this.options;
            this.running = !1, this.element.addClass("ui-tabs ui-widget ui-widget-content ui-corner-all").toggleClass("ui-tabs-collapsible", n.collapsible).delegate(".ui-tabs-nav > li", "mousedown" + this.eventNamespace, function(t) {
                e(this).is(".ui-state-disabled") && t.preventDefault();
            }).delegate(".ui-tabs-anchor", "focus" + this.eventNamespace, function() {
                e(this).closest("li").is(".ui-state-disabled") && this.blur();
            }), this._processTabs(), n.active = this._initialActive(), e.isArray(n.disabled) && (n.disabled = e.unique(n.disabled.concat(e.map(this.tabs.filter(".ui-state-disabled"), function(e) {
                return t.tabs.index(e);
            }))).sort()), this.active = this.options.active !== !1 && this.anchors.length ? this._findActive(n.active) : e(), this._refresh(), this.active.length && this.load(n.active);
        },
        _initialActive: function() {
            var n = this.options.active, r = this.options.collapsible, i = location.hash.substring(1);
            return null === n && (i && this.tabs.each(function(r, s) {
                return e(s).attr("aria-controls") === i ? (n = r, !1) : t;
            }), null === n && (n = this.tabs.index(this.tabs.filter(".ui-tabs-active"))), (null === n || -1 === n) && (n = this.tabs.length ? 0 : !1)), n !== !1 && (n = this.tabs.index(this.tabs.eq(n)), -1 === n && (n = r ? !1 : 0)), !r && n === !1 && this.anchors.length && (n = 0), n;
        },
        _getCreateEventData: function() {
            return {
                tab: this.active,
                panel: this.active.length ? this._getPanelForTab(this.active) : e()
            };
        },
        _tabKeydown: function(n) {
            var r = e(this.document[0].activeElement).closest("li"), i = this.tabs.index(r), s = !0;
            if (!this._handlePageNav(n)) {
                switch (n.keyCode) {
                  case e.ui.keyCode.RIGHT:
                  case e.ui.keyCode.DOWN:
                    i++;
                    break;
                  case e.ui.keyCode.UP:
                  case e.ui.keyCode.LEFT:
                    s = !1, i--;
                    break;
                  case e.ui.keyCode.END:
                    i = this.anchors.length - 1;
                    break;
                  case e.ui.keyCode.HOME:
                    i = 0;
                    break;
                  case e.ui.keyCode.SPACE:
                    return n.preventDefault(), clearTimeout(this.activating), this._activate(i), t;
                  case e.ui.keyCode.ENTER:
                    return n.preventDefault(), clearTimeout(this.activating), this._activate(i === this.options.active ? !1 : i), t;
                  default:
                    return;
                }
                n.preventDefault(), clearTimeout(this.activating), i = this._focusNextTab(i, s), n.ctrlKey || (r.attr("aria-selected", "false"), this.tabs.eq(i).attr("aria-selected", "true"), this.activating = this._delay(function() {
                    this.option("active", i);
                }, this.delay));
            }
        },
        _panelKeydown: function(t) {
            this._handlePageNav(t) || t.ctrlKey && t.keyCode === e.ui.keyCode.UP && (t.preventDefault(), this.active.focus());
        },
        _handlePageNav: function(n) {
            return n.altKey && n.keyCode === e.ui.keyCode.PAGE_UP ? (this._activate(this._focusNextTab(this.options.active - 1, !1)), !0) : n.altKey && n.keyCode === e.ui.keyCode.PAGE_DOWN ? (this._activate(this._focusNextTab(this.options.active + 1, !0)), !0) : t;
        },
        _findNextTab: function(t, n) {
            function r() {
                return t > i && (t = 0), 0 > t && (t = i), t;
            }
            for (var i = this.tabs.length - 1; -1 !== e.inArray(r(), this.options.disabled); ) t = n ? t + 1 : t - 1;
            return t;
        },
        _focusNextTab: function(e, t) {
            return e = this._findNextTab(e, t), this.tabs.eq(e).focus(), e;
        },
        _setOption: function(e, n) {
            return "active" === e ? (this._activate(n), t) : "disabled" === e ? (this._setupDisabled(n), t) : (this._super(e, n), "collapsible" === e && (this.element.toggleClass("ui-tabs-collapsible", n), n || this.options.active !== !1 || this._activate(0)), "event" === e && this._setupEvents(n), "heightStyle" === e && this._setupHeightStyle(n), t);
        },
        _tabId: function(e) {
            return e.attr("aria-controls") || "ui-tabs-" + n();
        },
        _sanitizeSelector: function(e) {
            return e ? e.replace(/[!"$%&'()*+,.\/:;<=>?@\[\]\^`{|}~]/g, "\\$&") : "";
        },
        refresh: function() {
            var t = this.options, n = this.tablist.children(":has(a[href])");
            t.disabled = e.map(n.filter(".ui-state-disabled"), function(e) {
                return n.index(e);
            }), this._processTabs(), t.active !== !1 && this.anchors.length ? this.active.length && !e.contains(this.tablist[0], this.active[0]) ? this.tabs.length === t.disabled.length ? (t.active = !1, this.active = e()) : this._activate(this._findNextTab(Math.max(0, t.active - 1), !1)) : t.active = this.tabs.index(this.active) : (t.active = !1, this.active = e()), this._refresh();
        },
        _refresh: function() {
            this._setupDisabled(this.options.disabled), this._setupEvents(this.options.event), this._setupHeightStyle(this.options.heightStyle), this.tabs.not(this.active).attr({
                "aria-selected": "false",
                tabIndex: -1
            }), this.panels.not(this._getPanelForTab(this.active)).hide().attr({
                "aria-expanded": "false",
                "aria-hidden": "true"
            }), this.active.length ? (this.active.addClass("ui-tabs-active ui-state-active").attr({
                "aria-selected": "true",
                tabIndex: 0
            }), this._getPanelForTab(this.active).show().attr({
                "aria-expanded": "true",
                "aria-hidden": "false"
            })) : this.tabs.eq(0).attr("tabIndex", 0);
        },
        _processTabs: function() {
            var t = this;
            this.tablist = this._getList().addClass("ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all").attr("role", "tablist"), this.tabs = this.tablist.find("> li:has(a[href])").addClass("ui-state-default ui-corner-top").attr({
                role: "tab",
                tabIndex: -1
            }), this.anchors = this.tabs.map(function() {
                return e("a", this)[0];
            }).addClass("ui-tabs-anchor").attr({
                role: "presentation",
                tabIndex: -1
            }), this.panels = e(), this.anchors.each(function(n, i) {
                var s, o, u, a = e(i).uniqueId().attr("id"), f = e(i).closest("li"), l = f.attr("aria-controls");
                r(i) ? (s = i.hash, o = t.element.find(t._sanitizeSelector(s))) : (u = t._tabId(f), s = "#" + u, o = t.element.find(s), o.length || (o = t._createPanel(u), o.insertAfter(t.panels[n - 1] || t.tablist)), o.attr("aria-live", "polite")), o.length && (t.panels = t.panels.add(o)), l && f.data("ui-tabs-aria-controls", l), f.attr({
                    "aria-controls": s.substring(1),
                    "aria-labelledby": a
                }), o.attr("aria-labelledby", a);
            }), this.panels.addClass("ui-tabs-panel ui-widget-content ui-corner-bottom").attr("role", "tabpanel");
        },
        _getList: function() {
            return this.element.find("ol,ul").eq(0);
        },
        _createPanel: function(t) {
            return e("<div>").attr("id", t).addClass("ui-tabs-panel ui-widget-content ui-corner-bottom").data("ui-tabs-destroy", !0);
        },
        _setupDisabled: function(t) {
            e.isArray(t) && (t.length ? t.length === this.anchors.length && (t = !0) : t = !1);
            for (var n, r = 0; n = this.tabs[r]; r++) t === !0 || -1 !== e.inArray(r, t) ? e(n).addClass("ui-state-disabled").attr("aria-disabled", "true") : e(n).removeClass("ui-state-disabled").removeAttr("aria-disabled");
            this.options.disabled = t;
        },
        _setupEvents: function(t) {
            var n = {
                click: function(e) {
                    e.preventDefault();
                }
            };
            t && e.each(t.split(" "), function(e, t) {
                n[t] = "_eventHandler";
            }), this._off(this.anchors.add(this.tabs).add(this.panels)), this._on(this.anchors, n), this._on(this.tabs, {
                keydown: "_tabKeydown"
            }), this._on(this.panels, {
                keydown: "_panelKeydown"
            }), this._focusable(this.tabs), this._hoverable(this.tabs);
        },
        _setupHeightStyle: function(t) {
            var n, r = this.element.parent();
            "fill" === t ? (n = r.height(), n -= this.element.outerHeight() - this.element.height(), this.element.siblings(":visible").each(function() {
                var t = e(this), r = t.css("position");
                "absolute" !== r && "fixed" !== r && (n -= t.outerHeight(!0));
            }), this.element.children().not(this.panels).each(function() {
                n -= e(this).outerHeight(!0);
            }), this.panels.each(function() {
                e(this).height(Math.max(0, n - e(this).innerHeight() + e(this).height()));
            }).css("overflow", "auto")) : "auto" === t && (n = 0, this.panels.each(function() {
                n = Math.max(n, e(this).height("").height());
            }).height(n));
        },
        _eventHandler: function(t) {
            var n = this.options, r = this.active, i = e(t.currentTarget), s = i.closest("li"), o = s[0] === r[0], u = o && n.collapsible, a = u ? e() : this._getPanelForTab(s), f = r.length ? this._getPanelForTab(r) : e(), l = {
                oldTab: r,
                oldPanel: f,
                newTab: u ? e() : s,
                newPanel: a
            };
            t.preventDefault(), s.hasClass("ui-state-disabled") || s.hasClass("ui-tabs-loading") || this.running || o && !n.collapsible || this._trigger("beforeActivate", t, l) === !1 || (n.active = u ? !1 : this.tabs.index(s), this.active = o ? e() : s, this.xhr && this.xhr.abort(), f.length || a.length || e.error("jQuery UI Tabs: Mismatching fragment identifier."), a.length && this.load(this.tabs.index(s), t), this._toggle(t, l));
        },
        _toggle: function(t, n) {
            function r() {
                s.running = !1, s._trigger("activate", t, n);
            }
            function i() {
                n.newTab.closest("li").addClass("ui-tabs-active ui-state-active"), o.length && s.options.show ? s._show(o, s.options.show, r) : (o.show(), r());
            }
            var s = this, o = n.newPanel, u = n.oldPanel;
            this.running = !0, u.length && this.options.hide ? this._hide(u, this.options.hide, function() {
                n.oldTab.closest("li").removeClass("ui-tabs-active ui-state-active"), i();
            }) : (n.oldTab.closest("li").removeClass("ui-tabs-active ui-state-active"), u.hide(), i()), u.attr({
                "aria-expanded": "false",
                "aria-hidden": "true"
            }), n.oldTab.attr("aria-selected", "false"), o.length && u.length ? n.oldTab.attr("tabIndex", -1) : o.length && this.tabs.filter(function() {
                return 0 === e(this).attr("tabIndex");
            }).attr("tabIndex", -1), o.attr({
                "aria-expanded": "true",
                "aria-hidden": "false"
            }), n.newTab.attr({
                "aria-selected": "true",
                tabIndex: 0
            });
        },
        _activate: function(t) {
            var n, r = this._findActive(t);
            r[0] !== this.active[0] && (r.length || (r = this.active), n = r.find(".ui-tabs-anchor")[0], this._eventHandler({
                target: n,
                currentTarget: n,
                preventDefault: e.noop
            }));
        },
        _findActive: function(t) {
            return t === !1 ? e() : this.tabs.eq(t);
        },
        _getIndex: function(e) {
            return "string" == typeof e && (e = this.anchors.index(this.anchors.filter("[href$='" + e + "']"))), e;
        },
        _destroy: function() {
            this.xhr && this.xhr.abort(), this.element.removeClass("ui-tabs ui-widget ui-widget-content ui-corner-all ui-tabs-collapsible"), this.tablist.removeClass("ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all").removeAttr("role"), this.anchors.removeClass("ui-tabs-anchor").removeAttr("role").removeAttr("tabIndex").removeUniqueId(), this.tabs.add(this.panels).each(function() {
                e.data(this, "ui-tabs-destroy") ? e(this).remove() : e(this).removeClass("ui-state-default ui-state-active ui-state-disabled ui-corner-top ui-corner-bottom ui-widget-content ui-tabs-active ui-tabs-panel").removeAttr("tabIndex").removeAttr("aria-live").removeAttr("aria-busy").removeAttr("aria-selected").removeAttr("aria-labelledby").removeAttr("aria-hidden").removeAttr("aria-expanded").removeAttr("role");
            }), this.tabs.each(function() {
                var t = e(this), n = t.data("ui-tabs-aria-controls");
                n ? t.attr("aria-controls", n).removeData("ui-tabs-aria-controls") : t.removeAttr("aria-controls");
            }), this.panels.show(), "content" !== this.options.heightStyle && this.panels.css("height", "");
        },
        enable: function(n) {
            var r = this.options.disabled;
            r !== !1 && (n === t ? r = !1 : (n = this._getIndex(n), r = e.isArray(r) ? e.map(r, function(e) {
                return e !== n ? e : null;
            }) : e.map(this.tabs, function(e, t) {
                return t !== n ? t : null;
            })), this._setupDisabled(r));
        },
        disable: function(n) {
            var r = this.options.disabled;
            if (r !== !0) {
                if (n === t) r = !0; else {
                    if (n = this._getIndex(n), -1 !== e.inArray(n, r)) return;
                    r = e.isArray(r) ? e.merge([ n ], r).sort() : [ n ];
                }
                this._setupDisabled(r);
            }
        },
        load: function(t, n) {
            t = this._getIndex(t);
            var i = this, s = this.tabs.eq(t), o = s.find(".ui-tabs-anchor"), u = this._getPanelForTab(s), a = {
                tab: s,
                panel: u
            };
            r(o[0]) || (this.xhr = e.ajax(this._ajaxSettings(o, n, a)), this.xhr && "canceled" !== this.xhr.statusText && (s.addClass("ui-tabs-loading"), u.attr("aria-busy", "true"), this.xhr.success(function(e) {
                setTimeout(function() {
                    u.html(e), i._trigger("load", n, a);
                }, 1);
            }).complete(function(e, t) {
                setTimeout(function() {
                    "abort" === t && i.panels.stop(!1, !0), s.removeClass("ui-tabs-loading"), u.removeAttr("aria-busy"), e === i.xhr && delete i.xhr;
                }, 1);
            })));
        },
        _ajaxSettings: function(t, n, r) {
            var i = this;
            return {
                url: t.attr("href"),
                beforeSend: function(t, s) {
                    return i._trigger("beforeLoad", n, e.extend({
                        jqXHR: t,
                        ajaxSettings: s
                    }, r));
                }
            };
        },
        _getPanelForTab: function(t) {
            var n = e(t).attr("aria-controls");
            return this.element.find(this._sanitizeSelector("#" + n));
        }
    });
})(jQuery);

(function(e) {
    function t(t, n) {
        var r = (t.attr("aria-describedby") || "").split(/\s+/);
        r.push(n), t.data("ui-tooltip-id", n).attr("aria-describedby", e.trim(r.join(" ")));
    }
    function n(t) {
        var n = t.data("ui-tooltip-id"), r = (t.attr("aria-describedby") || "").split(/\s+/), i = e.inArray(n, r);
        -1 !== i && r.splice(i, 1), t.removeData("ui-tooltip-id"), r = e.trim(r.join(" ")), r ? t.attr("aria-describedby", r) : t.removeAttr("aria-describedby");
    }
    var r = 0;
    e.widget("ui.tooltip", {
        version: "1.10.3",
        options: {
            content: function() {
                var t = e(this).attr("title") || "";
                return e("<a>").text(t).html();
            },
            hide: !0,
            items: "[title]:not([disabled])",
            position: {
                my: "left top+15",
                at: "left bottom",
                collision: "flipfit flip"
            },
            show: !0,
            tooltipClass: null,
            track: !1,
            close: null,
            open: null
        },
        _create: function() {
            this._on({
                mouseover: "open",
                focusin: "open"
            }), this.tooltips = {}, this.parents = {}, this.options.disabled && this._disable();
        },
        _setOption: function(t, n) {
            var r = this;
            return "disabled" === t ? (this[n ? "_disable" : "_enable"](), this.options[t] = n, void 0) : (this._super(t, n), "content" === t && e.each(this.tooltips, function(e, t) {
                r._updateContent(t);
            }), void 0);
        },
        _disable: function() {
            var t = this;
            e.each(this.tooltips, function(n, r) {
                var i = e.Event("blur");
                i.target = i.currentTarget = r[0], t.close(i, !0);
            }), this.element.find(this.options.items).addBack().each(function() {
                var t = e(this);
                t.is("[title]") && t.data("ui-tooltip-title", t.attr("title")).attr("title", "");
            });
        },
        _enable: function() {
            this.element.find(this.options.items).addBack().each(function() {
                var t = e(this);
                t.data("ui-tooltip-title") && t.attr("title", t.data("ui-tooltip-title"));
            });
        },
        open: function(t) {
            var n = this, r = e(t ? t.target : this.element).closest(this.options.items);
            r.length && !r.data("ui-tooltip-id") && (r.attr("title") && r.data("ui-tooltip-title", r.attr("title")), r.data("ui-tooltip-open", !0), t && "mouseover" === t.type && r.parents().each(function() {
                var t, r = e(this);
                r.data("ui-tooltip-open") && (t = e.Event("blur"), t.target = t.currentTarget = this, n.close(t, !0)), r.attr("title") && (r.uniqueId(), n.parents[this.id] = {
                    element: this,
                    title: r.attr("title")
                }, r.attr("title", ""));
            }), this._updateContent(r, t));
        },
        _updateContent: function(e, t) {
            var n, r = this.options.content, i = this, s = t ? t.type : null;
            return "string" == typeof r ? this._open(t, e, r) : (n = r.call(e[0], function(n) {
                e.data("ui-tooltip-open") && i._delay(function() {
                    t && (t.type = s), this._open(t, e, n);
                });
            }), n && this._open(t, e, n), void 0);
        },
        _open: function(n, r, i) {
            function s(e) {
                f.of = e, o.is(":hidden") || o.position(f);
            }
            var o, u, a, f = e.extend({}, this.options.position);
            if (i) {
                if (o = this._find(r), o.length) return o.find(".ui-tooltip-content").html(i), void 0;
                r.is("[title]") && (n && "mouseover" === n.type ? r.attr("title", "") : r.removeAttr("title")), o = this._tooltip(r), t(r, o.attr("id")), o.find(".ui-tooltip-content").html(i), this.options.track && n && /^mouse/.test(n.type) ? (this._on(this.document, {
                    mousemove: s
                }), s(n)) : o.position(e.extend({
                    of: r
                }, this.options.position)), o.hide(), this._show(o, this.options.show), this.options.show && this.options.show.delay && (a = this.delayedShow = setInterval(function() {
                    o.is(":visible") && (s(f.of), clearInterval(a));
                }, e.fx.interval)), this._trigger("open", n, {
                    tooltip: o
                }), u = {
                    keyup: function(t) {
                        if (t.keyCode === e.ui.keyCode.ESCAPE) {
                            var n = e.Event(t);
                            n.currentTarget = r[0], this.close(n, !0);
                        }
                    },
                    remove: function() {
                        this._removeTooltip(o);
                    }
                }, n && "mouseover" !== n.type || (u.mouseleave = "close"), n && "focusin" !== n.type || (u.focusout = "close"), this._on(!0, r, u);
            }
        },
        close: function(t) {
            var r = this, s = e(t ? t.currentTarget : this.element), o = this._find(s);
            this.closing || (clearInterval(this.delayedShow), s.data("ui-tooltip-title") && s.attr("title", s.data("ui-tooltip-title")), n(s), o.stop(!0), this._hide(o, this.options.hide, function() {
                r._removeTooltip(e(this));
            }), s.removeData("ui-tooltip-open"), this._off(s, "mouseleave focusout keyup"), s[0] !== this.element[0] && this._off(s, "remove"), this._off(this.document, "mousemove"), t && "mouseleave" === t.type && e.each(this.parents, function(t, n) {
                e(n.element).attr("title", n.title), delete r.parents[t];
            }), this.closing = !0, this._trigger("close", t, {
                tooltip: o
            }), this.closing = !1);
        },
        _tooltip: function(t) {
            var n = "ui-tooltip-" + r++, i = e("<div>").attr({
                id: n,
                role: "tooltip"
            }).addClass("ui-tooltip ui-widget ui-corner-all ui-widget-content " + (this.options.tooltipClass || ""));
            return e("<div>").addClass("ui-tooltip-content").appendTo(i), i.appendTo(this.document[0].body), this.tooltips[n] = t, i;
        },
        _find: function(t) {
            var n = t.data("ui-tooltip-id");
            return n ? e("#" + n) : e();
        },
        _removeTooltip: function(e) {
            e.remove(), delete this.tooltips[e.attr("id")];
        },
        _destroy: function() {
            var t = this;
            e.each(this.tooltips, function(n, r) {
                var i = e.Event("blur");
                i.target = i.currentTarget = r[0], t.close(i, !0), e("#" + n).remove(), r.data("ui-tooltip-title") && (r.attr("title", r.data("ui-tooltip-title")), r.removeData("ui-tooltip-title"));
            });
        }
    });
})(jQuery);

(function(e, t) {
    var n = "ui-effects-";
    e.effects = {
        effect: {}
    }, function(e, t) {
        function n(e, t, n) {
            var r = c[t.type] || {};
            return null == e ? n || !t.def ? null : t.def : (e = r.floor ? ~~e : parseFloat(e), isNaN(e) ? t.def : r.mod ? (e + r.mod) % r.mod : 0 > e ? 0 : e > r.max ? r.max : e);
        }
        function r(n) {
            var r = f(), i = r._rgba = [];
            return n = n.toLowerCase(), d(a, function(e, s) {
                var o, u = s.re.exec(n), a = u && s.parse(u), f = s.space || "rgba";
                return a ? (o = r[f](a), r[l[f].cache] = o[l[f].cache], i = r._rgba = o._rgba, !1) : t;
            }), i.length ? ("0,0,0,0" === i.join() && e.extend(i, s.transparent), r) : s[n];
        }
        function i(e, t, n) {
            return n = (n + 1) % 1, 1 > 6 * n ? e + 6 * (t - e) * n : 1 > 2 * n ? t : 2 > 3 * n ? e + 6 * (t - e) * (2 / 3 - n) : e;
        }
        var s, o = "backgroundColor borderBottomColor borderLeftColor borderRightColor borderTopColor color columnRuleColor outlineColor textDecorationColor textEmphasisColor", u = /^([\-+])=\s*(\d+\.?\d*)/, a = [ {
            re: /rgba?\(\s*(\d{1,3})\s*,\s*(\d{1,3})\s*,\s*(\d{1,3})\s*(?:,\s*(\d?(?:\.\d+)?)\s*)?\)/,
            parse: function(e) {
                return [ e[1], e[2], e[3], e[4] ];
            }
        }, {
            re: /rgba?\(\s*(\d+(?:\.\d+)?)\%\s*,\s*(\d+(?:\.\d+)?)\%\s*,\s*(\d+(?:\.\d+)?)\%\s*(?:,\s*(\d?(?:\.\d+)?)\s*)?\)/,
            parse: function(e) {
                return [ 2.55 * e[1], 2.55 * e[2], 2.55 * e[3], e[4] ];
            }
        }, {
            re: /#([a-f0-9]{2})([a-f0-9]{2})([a-f0-9]{2})/,
            parse: function(e) {
                return [ parseInt(e[1], 16), parseInt(e[2], 16), parseInt(e[3], 16) ];
            }
        }, {
            re: /#([a-f0-9])([a-f0-9])([a-f0-9])/,
            parse: function(e) {
                return [ parseInt(e[1] + e[1], 16), parseInt(e[2] + e[2], 16), parseInt(e[3] + e[3], 16) ];
            }
        }, {
            re: /hsla?\(\s*(\d+(?:\.\d+)?)\s*,\s*(\d+(?:\.\d+)?)\%\s*,\s*(\d+(?:\.\d+)?)\%\s*(?:,\s*(\d?(?:\.\d+)?)\s*)?\)/,
            space: "hsla",
            parse: function(e) {
                return [ e[1], e[2] / 100, e[3] / 100, e[4] ];
            }
        } ], f = e.Color = function(t, n, r, i) {
            return new e.Color.fn.parse(t, n, r, i);
        }, l = {
            rgba: {
                props: {
                    red: {
                        idx: 0,
                        type: "byte"
                    },
                    green: {
                        idx: 1,
                        type: "byte"
                    },
                    blue: {
                        idx: 2,
                        type: "byte"
                    }
                }
            },
            hsla: {
                props: {
                    hue: {
                        idx: 0,
                        type: "degrees"
                    },
                    saturation: {
                        idx: 1,
                        type: "percent"
                    },
                    lightness: {
                        idx: 2,
                        type: "percent"
                    }
                }
            }
        }, c = {
            "byte": {
                floor: !0,
                max: 255
            },
            percent: {
                max: 1
            },
            degrees: {
                mod: 360,
                floor: !0
            }
        }, h = f.support = {}, p = e("<p>")[0], d = e.each;
        p.style.cssText = "background-color:rgba(1,1,1,.5)", h.rgba = p.style.backgroundColor.indexOf("rgba") > -1, d(l, function(e, t) {
            t.cache = "_" + e, t.props.alpha = {
                idx: 3,
                type: "percent",
                def: 1
            };
        }), f.fn = e.extend(f.prototype, {
            parse: function(i, o, u, a) {
                if (i === t) return this._rgba = [ null, null, null, null ], this;
                (i.jquery || i.nodeType) && (i = e(i).css(o), o = t);
                var c = this, h = e.type(i), p = this._rgba = [];
                return o !== t && (i = [ i, o, u, a ], h = "array"), "string" === h ? this.parse(r(i) || s._default) : "array" === h ? (d(l.rgba.props, function(e, t) {
                    p[t.idx] = n(i[t.idx], t);
                }), this) : "object" === h ? (i instanceof f ? d(l, function(e, t) {
                    i[t.cache] && (c[t.cache] = i[t.cache].slice());
                }) : d(l, function(t, r) {
                    var s = r.cache;
                    d(r.props, function(e, t) {
                        if (!c[s] && r.to) {
                            if ("alpha" === e || null == i[e]) return;
                            c[s] = r.to(c._rgba);
                        }
                        c[s][t.idx] = n(i[e], t, !0);
                    }), c[s] && 0 > e.inArray(null, c[s].slice(0, 3)) && (c[s][3] = 1, r.from && (c._rgba = r.from(c[s])));
                }), this) : t;
            },
            is: function(e) {
                var n = f(e), r = !0, i = this;
                return d(l, function(e, s) {
                    var o, u = n[s.cache];
                    return u && (o = i[s.cache] || s.to && s.to(i._rgba) || [], d(s.props, function(e, n) {
                        return null != u[n.idx] ? r = u[n.idx] === o[n.idx] : t;
                    })), r;
                }), r;
            },
            _space: function() {
                var e = [], t = this;
                return d(l, function(n, r) {
                    t[r.cache] && e.push(n);
                }), e.pop();
            },
            transition: function(e, t) {
                var r = f(e), i = r._space(), s = l[i], o = 0 === this.alpha() ? f("transparent") : this, u = o[s.cache] || s.to(o._rgba), a = u.slice();
                return r = r[s.cache], d(s.props, function(e, i) {
                    var s = i.idx, o = u[s], f = r[s], l = c[i.type] || {};
                    null !== f && (null === o ? a[s] = f : (l.mod && (f - o > l.mod / 2 ? o += l.mod : o - f > l.mod / 2 && (o -= l.mod)), a[s] = n((f - o) * t + o, i)));
                }), this[i](a);
            },
            blend: function(t) {
                if (1 === this._rgba[3]) return this;
                var n = this._rgba.slice(), r = n.pop(), i = f(t)._rgba;
                return f(e.map(n, function(e, t) {
                    return (1 - r) * i[t] + r * e;
                }));
            },
            toRgbaString: function() {
                var t = "rgba(", n = e.map(this._rgba, function(e, t) {
                    return null == e ? t > 2 ? 1 : 0 : e;
                });
                return 1 === n[3] && (n.pop(), t = "rgb("), t + n.join() + ")";
            },
            toHslaString: function() {
                var t = "hsla(", n = e.map(this.hsla(), function(e, t) {
                    return null == e && (e = t > 2 ? 1 : 0), t && 3 > t && (e = Math.round(100 * e) + "%"), e;
                });
                return 1 === n[3] && (n.pop(), t = "hsl("), t + n.join() + ")";
            },
            toHexString: function(t) {
                var n = this._rgba.slice(), r = n.pop();
                return t && n.push(~~(255 * r)), "#" + e.map(n, function(e) {
                    return e = (e || 0).toString(16), 1 === e.length ? "0" + e : e;
                }).join("");
            },
            toString: function() {
                return 0 === this._rgba[3] ? "transparent" : this.toRgbaString();
            }
        }), f.fn.parse.prototype = f.fn, l.hsla.to = function(e) {
            if (null == e[0] || null == e[1] || null == e[2]) return [ null, null, null, e[3] ];
            var t, n, r = e[0] / 255, i = e[1] / 255, s = e[2] / 255, o = e[3], u = Math.max(r, i, s), a = Math.min(r, i, s), f = u - a, l = u + a, c = .5 * l;
            return t = a === u ? 0 : r === u ? 60 * (i - s) / f + 360 : i === u ? 60 * (s - r) / f + 120 : 60 * (r - i) / f + 240, n = 0 === f ? 0 : .5 >= c ? f / l : f / (2 - l), [ Math.round(t) % 360, n, c, null == o ? 1 : o ];
        }, l.hsla.from = function(e) {
            if (null == e[0] || null == e[1] || null == e[2]) return [ null, null, null, e[3] ];
            var t = e[0] / 360, n = e[1], r = e[2], s = e[3], o = .5 >= r ? r * (1 + n) : r + n - r * n, u = 2 * r - o;
            return [ Math.round(255 * i(u, o, t + 1 / 3)), Math.round(255 * i(u, o, t)), Math.round(255 * i(u, o, t - 1 / 3)), s ];
        }, d(l, function(r, i) {
            var s = i.props, o = i.cache, a = i.to, l = i.from;
            f.fn[r] = function(r) {
                if (a && !this[o] && (this[o] = a(this._rgba)), r === t) return this[o].slice();
                var i, u = e.type(r), c = "array" === u || "object" === u ? r : arguments, h = this[o].slice();
                return d(s, function(e, t) {
                    var r = c["object" === u ? e : t.idx];
                    null == r && (r = h[t.idx]), h[t.idx] = n(r, t);
                }), l ? (i = f(l(h)), i[o] = h, i) : f(h);
            }, d(s, function(t, n) {
                f.fn[t] || (f.fn[t] = function(i) {
                    var s, o = e.type(i), a = "alpha" === t ? this._hsla ? "hsla" : "rgba" : r, f = this[a](), l = f[n.idx];
                    return "undefined" === o ? l : ("function" === o && (i = i.call(this, l), o = e.type(i)), null == i && n.empty ? this : ("string" === o && (s = u.exec(i), s && (i = l + parseFloat(s[2]) * ("+" === s[1] ? 1 : -1))), f[n.idx] = i, this[a](f)));
                });
            });
        }), f.hook = function(t) {
            var n = t.split(" ");
            d(n, function(t, n) {
                e.cssHooks[n] = {
                    set: function(t, i) {
                        var s, o, u = "";
                        if ("transparent" !== i && ("string" !== e.type(i) || (s = r(i)))) {
                            if (i = f(s || i), !h.rgba && 1 !== i._rgba[3]) {
                                for (o = "backgroundColor" === n ? t.parentNode : t; ("" === u || "transparent" === u) && o && o.style; ) try {
                                    u = e.css(o, "backgroundColor"), o = o.parentNode;
                                } catch (a) {}
                                i = i.blend(u && "transparent" !== u ? u : "_default");
                            }
                            i = i.toRgbaString();
                        }
                        try {
                            t.style[n] = i;
                        } catch (a) {}
                    }
                }, e.fx.step[n] = function(t) {
                    t.colorInit || (t.start = f(t.elem, n), t.end = f(t.end), t.colorInit = !0), e.cssHooks[n].set(t.elem, t.start.transition(t.end, t.pos));
                };
            });
        }, f.hook(o), e.cssHooks.borderColor = {
            expand: function(e) {
                var t = {};
                return d([ "Top", "Right", "Bottom", "Left" ], function(n, r) {
                    t["border" + r + "Color"] = e;
                }), t;
            }
        }, s = e.Color.names = {
            aqua: "#00ffff",
            black: "#000000",
            blue: "#0000ff",
            fuchsia: "#ff00ff",
            gray: "#808080",
            green: "#008000",
            lime: "#00ff00",
            maroon: "#800000",
            navy: "#000080",
            olive: "#808000",
            purple: "#800080",
            red: "#ff0000",
            silver: "#c0c0c0",
            teal: "#008080",
            white: "#ffffff",
            yellow: "#ffff00",
            transparent: [ null, null, null, 0 ],
            _default: "#ffffff"
        };
    }(jQuery), function() {
        function n(t) {
            var n, r, i = t.ownerDocument.defaultView ? t.ownerDocument.defaultView.getComputedStyle(t, null) : t.currentStyle, s = {};
            if (i && i.length && i[0] && i[i[0]]) for (r = i.length; r--; ) n = i[r], "string" == typeof i[n] && (s[e.camelCase(n)] = i[n]); else for (n in i) "string" == typeof i[n] && (s[n] = i[n]);
            return s;
        }
        function r(t, n) {
            var r, i, u = {};
            for (r in n) i = n[r], t[r] !== i && (s[r] || (e.fx.step[r] || !isNaN(parseFloat(i))) && (u[r] = i));
            return u;
        }
        var i = [ "add", "remove", "toggle" ], s = {
            border: 1,
            borderBottom: 1,
            borderColor: 1,
            borderLeft: 1,
            borderRight: 1,
            borderTop: 1,
            borderWidth: 1,
            margin: 1,
            padding: 1
        };
        e.each([ "borderLeftStyle", "borderRightStyle", "borderBottomStyle", "borderTopStyle" ], function(t, n) {
            e.fx.step[n] = function(e) {
                ("none" !== e.end && !e.setAttr || 1 === e.pos && !e.setAttr) && (jQuery.style(e.elem, n, e.end), e.setAttr = !0);
            };
        }), e.fn.addBack || (e.fn.addBack = function(e) {
            return this.add(null == e ? this.prevObject : this.prevObject.filter(e));
        }), e.effects.animateClass = function(t, s, o, u) {
            var a = e.speed(s, o, u);
            return this.queue(function() {
                var s, o = e(this), u = o.attr("class") || "", f = a.children ? o.find("*").addBack() : o;
                f = f.map(function() {
                    var t = e(this);
                    return {
                        el: t,
                        start: n(this)
                    };
                }), s = function() {
                    e.each(i, function(e, n) {
                        t[n] && o[n + "Class"](t[n]);
                    });
                }, s(), f = f.map(function() {
                    return this.end = n(this.el[0]), this.diff = r(this.start, this.end), this;
                }), o.attr("class", u), f = f.map(function() {
                    var t = this, n = e.Deferred(), r = e.extend({}, a, {
                        queue: !1,
                        complete: function() {
                            n.resolve(t);
                        }
                    });
                    return this.el.animate(this.diff, r), n.promise();
                }), e.when.apply(e, f.get()).done(function() {
                    s(), e.each(arguments, function() {
                        var t = this.el;
                        e.each(this.diff, function(e) {
                            t.css(e, "");
                        });
                    }), a.complete.call(o[0]);
                });
            });
        }, e.fn.extend({
            addClass: function(t) {
                return function(n, r, i, s) {
                    return r ? e.effects.animateClass.call(this, {
                        add: n
                    }, r, i, s) : t.apply(this, arguments);
                };
            }(e.fn.addClass),
            removeClass: function(t) {
                return function(n, r, i, s) {
                    return arguments.length > 1 ? e.effects.animateClass.call(this, {
                        remove: n
                    }, r, i, s) : t.apply(this, arguments);
                };
            }(e.fn.removeClass),
            toggleClass: function(n) {
                return function(r, i, s, o, u) {
                    return "boolean" == typeof i || i === t ? s ? e.effects.animateClass.call(this, i ? {
                        add: r
                    } : {
                        remove: r
                    }, s, o, u) : n.apply(this, arguments) : e.effects.animateClass.call(this, {
                        toggle: r
                    }, i, s, o);
                };
            }(e.fn.toggleClass),
            switchClass: function(t, n, r, i, s) {
                return e.effects.animateClass.call(this, {
                    add: n,
                    remove: t
                }, r, i, s);
            }
        });
    }(), function() {
        function r(t, n, r, i) {
            return e.isPlainObject(t) && (n = t, t = t.effect), t = {
                effect: t
            }, null == n && (n = {}), e.isFunction(n) && (i = n, r = null, n = {}), ("number" == typeof n || e.fx.speeds[n]) && (i = r, r = n, n = {}), e.isFunction(r) && (i = r, r = null), n && e.extend(t, n), r = r || n.duration, t.duration = e.fx.off ? 0 : "number" == typeof r ? r : r in e.fx.speeds ? e.fx.speeds[r] : e.fx.speeds._default, t.complete = i || n.complete, t;
        }
        function s(t) {
            return !t || "number" == typeof t || e.fx.speeds[t] ? !0 : "string" != typeof t || e.effects.effect[t] ? e.isFunction(t) ? !0 : "object" != typeof t || t.effect ? !1 : !0 : !0;
        }
        e.extend(e.effects, {
            version: "1.10.3",
            save: function(e, t) {
                for (var r = 0; t.length > r; r++) null !== t[r] && e.data(n + t[r], e[0].style[t[r]]);
            },
            restore: function(e, r) {
                var s, o;
                for (o = 0; r.length > o; o++) null !== r[o] && (s = e.data(n + r[o]), s === t && (s = ""), e.css(r[o], s));
            },
            setMode: function(e, t) {
                return "toggle" === t && (t = e.is(":hidden") ? "show" : "hide"), t;
            },
            getBaseline: function(e, t) {
                var n, r;
                switch (e[0]) {
                  case "top":
                    n = 0;
                    break;
                  case "middle":
                    n = .5;
                    break;
                  case "bottom":
                    n = 1;
                    break;
                  default:
                    n = e[0] / t.height;
                }
                switch (e[1]) {
                  case "left":
                    r = 0;
                    break;
                  case "center":
                    r = .5;
                    break;
                  case "right":
                    r = 1;
                    break;
                  default:
                    r = e[1] / t.width;
                }
                return {
                    x: r,
                    y: n
                };
            },
            createWrapper: function(t) {
                if (t.parent().is(".ui-effects-wrapper")) return t.parent();
                var n = {
                    width: t.outerWidth(!0),
                    height: t.outerHeight(!0),
                    "float": t.css("float")
                }, r = e("<div></div>").addClass("ui-effects-wrapper").css({
                    fontSize: "100%",
                    background: "transparent",
                    border: "none",
                    margin: 0,
                    padding: 0
                }), i = {
                    width: t.width(),
                    height: t.height()
                }, s = document.activeElement;
                try {
                    s.id;
                } catch (o) {
                    s = document.body;
                }
                return t.wrap(r), (t[0] === s || e.contains(t[0], s)) && e(s).focus(), r = t.parent(), "static" === t.css("position") ? (r.css({
                    position: "relative"
                }), t.css({
                    position: "relative"
                })) : (e.extend(n, {
                    position: t.css("position"),
                    zIndex: t.css("z-index")
                }), e.each([ "top", "left", "bottom", "right" ], function(e, r) {
                    n[r] = t.css(r), isNaN(parseInt(n[r], 10)) && (n[r] = "auto");
                }), t.css({
                    position: "relative",
                    top: 0,
                    left: 0,
                    right: "auto",
                    bottom: "auto"
                })), t.css(i), r.css(n).show();
            },
            removeWrapper: function(t) {
                var n = document.activeElement;
                return t.parent().is(".ui-effects-wrapper") && (t.parent().replaceWith(t), (t[0] === n || e.contains(t[0], n)) && e(n).focus()), t;
            },
            setTransition: function(t, n, r, i) {
                return i = i || {}, e.each(n, function(e, n) {
                    var s = t.cssUnit(n);
                    s[0] > 0 && (i[n] = s[0] * r + s[1]);
                }), i;
            }
        }), e.fn.extend({
            effect: function() {
                function t(t) {
                    function r() {
                        e.isFunction(s) && s.call(i[0]), e.isFunction(t) && t();
                    }
                    var i = e(this), s = n.complete, u = n.mode;
                    (i.is(":hidden") ? "hide" === u : "show" === u) ? (i[u](), r()) : o.call(i[0], n, r);
                }
                var n = r.apply(this, arguments), i = n.mode, s = n.queue, o = e.effects.effect[n.effect];
                return e.fx.off || !o ? i ? this[i](n.duration, n.complete) : this.each(function() {
                    n.complete && n.complete.call(this);
                }) : s === !1 ? this.each(t) : this.queue(s || "fx", t);
            },
            show: function(e) {
                return function(t) {
                    if (s(t)) return e.apply(this, arguments);
                    var n = r.apply(this, arguments);
                    return n.mode = "show", this.effect.call(this, n);
                };
            }(e.fn.show),
            hide: function(e) {
                return function(t) {
                    if (s(t)) return e.apply(this, arguments);
                    var n = r.apply(this, arguments);
                    return n.mode = "hide", this.effect.call(this, n);
                };
            }(e.fn.hide),
            toggle: function(e) {
                return function(t) {
                    if (s(t) || "boolean" == typeof t) return e.apply(this, arguments);
                    var n = r.apply(this, arguments);
                    return n.mode = "toggle", this.effect.call(this, n);
                };
            }(e.fn.toggle),
            cssUnit: function(t) {
                var n = this.css(t), r = [];
                return e.each([ "em", "px", "%", "pt" ], function(e, t) {
                    n.indexOf(t) > 0 && (r = [ parseFloat(n), t ]);
                }), r;
            }
        });
    }(), function() {
        var t = {};
        e.each([ "Quad", "Cubic", "Quart", "Quint", "Expo" ], function(e, n) {
            t[n] = function(t) {
                return Math.pow(t, e + 2);
            };
        }), e.extend(t, {
            Sine: function(e) {
                return 1 - Math.cos(e * Math.PI / 2);
            },
            Circ: function(e) {
                return 1 - Math.sqrt(1 - e * e);
            },
            Elastic: function(e) {
                return 0 === e || 1 === e ? e : -Math.pow(2, 8 * (e - 1)) * Math.sin((80 * (e - 1) - 7.5) * Math.PI / 15);
            },
            Back: function(e) {
                return e * e * (3 * e - 2);
            },
            Bounce: function(e) {
                for (var t, n = 4; ((t = Math.pow(2, --n)) - 1) / 11 > e; ) ;
                return 1 / Math.pow(4, 3 - n) - 7.5625 * Math.pow((3 * t - 2) / 22 - e, 2);
            }
        }), e.each(t, function(t, n) {
            e.easing["easeIn" + t] = n, e.easing["easeOut" + t] = function(e) {
                return 1 - n(1 - e);
            }, e.easing["easeInOut" + t] = function(e) {
                return .5 > e ? n(2 * e) / 2 : 1 - n(-2 * e + 2) / 2;
            };
        });
    }();
})(jQuery);

(function(e) {
    var t = /up|down|vertical/, n = /up|left|vertical|horizontal/;
    e.effects.effect.blind = function(r, s) {
        var o, u, a, f = e(this), l = [ "position", "top", "bottom", "left", "right", "height", "width" ], c = e.effects.setMode(f, r.mode || "hide"), h = r.direction || "up", p = t.test(h), d = p ? "height" : "width", v = p ? "top" : "left", m = n.test(h), g = {}, y = "show" === c;
        f.parent().is(".ui-effects-wrapper") ? e.effects.save(f.parent(), l) : e.effects.save(f, l), f.show(), o = e.effects.createWrapper(f).css({
            overflow: "hidden"
        }), u = o[d](), a = parseFloat(o.css(v)) || 0, g[d] = y ? u : 0, m || (f.css(p ? "bottom" : "right", 0).css(p ? "top" : "left", "auto").css({
            position: "absolute"
        }), g[v] = y ? a : u + a), y && (o.css(d, 0), m || o.css(v, a + u)), o.animate(g, {
            duration: r.duration,
            easing: r.easing,
            queue: !1,
            complete: function() {
                "hide" === c && f.hide(), e.effects.restore(f, l), e.effects.removeWrapper(f), s();
            }
        });
    };
})(jQuery);

(function(e) {
    e.effects.effect.bounce = function(n, r) {
        var i, s, o, u = e(this), a = [ "position", "top", "bottom", "left", "right", "height", "width" ], f = e.effects.setMode(u, n.mode || "effect"), l = "hide" === f, c = "show" === f, h = n.direction || "up", p = n.distance, d = n.times || 5, v = 2 * d + (c || l ? 1 : 0), m = n.duration / v, g = n.easing, y = "up" === h || "down" === h ? "top" : "left", b = "up" === h || "left" === h, w = u.queue(), E = w.length;
        for ((c || l) && a.push("opacity"), e.effects.save(u, a), u.show(), e.effects.createWrapper(u), p || (p = u["top" === y ? "outerHeight" : "outerWidth"]() / 3), c && (o = {
            opacity: 1
        }, o[y] = 0, u.css("opacity", 0).css(y, b ? 2 * -p : 2 * p).animate(o, m, g)), l && (p /= Math.pow(2, d - 1)), o = {}, o[y] = 0, i = 0; d > i; i++) s = {}, s[y] = (b ? "-=" : "+=") + p, u.animate(s, m, g).animate(o, m, g), p = l ? 2 * p : p / 2;
        l && (s = {
            opacity: 0
        }, s[y] = (b ? "-=" : "+=") + p, u.animate(s, m, g)), u.queue(function() {
            l && u.hide(), e.effects.restore(u, a), e.effects.removeWrapper(u), r();
        }), E > 1 && w.splice.apply(w, [ 1, 0 ].concat(w.splice(E, v + 1))), u.dequeue();
    };
})(jQuery);

(function(e) {
    e.effects.effect.clip = function(n, r) {
        var i, s, o, u = e(this), a = [ "position", "top", "bottom", "left", "right", "height", "width" ], f = e.effects.setMode(u, n.mode || "hide"), l = "show" === f, c = n.direction || "vertical", h = "vertical" === c, p = h ? "height" : "width", d = h ? "top" : "left", v = {};
        e.effects.save(u, a), u.show(), i = e.effects.createWrapper(u).css({
            overflow: "hidden"
        }), s = "IMG" === u[0].tagName ? i : u, o = s[p](), l && (s.css(p, 0), s.css(d, o / 2)), v[p] = l ? o : 0, v[d] = l ? 0 : o / 2, s.animate(v, {
            queue: !1,
            duration: n.duration,
            easing: n.easing,
            complete: function() {
                l || u.hide(), e.effects.restore(u, a), e.effects.removeWrapper(u), r();
            }
        });
    };
})(jQuery);

(function(e) {
    e.effects.effect.drop = function(n, r) {
        var i, s = e(this), o = [ "position", "top", "bottom", "left", "right", "opacity", "height", "width" ], u = e.effects.setMode(s, n.mode || "hide"), a = "show" === u, f = n.direction || "left", l = "up" === f || "down" === f ? "top" : "left", c = "up" === f || "left" === f ? "pos" : "neg", h = {
            opacity: a ? 1 : 0
        };
        e.effects.save(s, o), s.show(), e.effects.createWrapper(s), i = n.distance || s["top" === l ? "outerHeight" : "outerWidth"](!0) / 2, a && s.css("opacity", 0).css(l, "pos" === c ? -i : i), h[l] = (a ? "pos" === c ? "+=" : "-=" : "pos" === c ? "-=" : "+=") + i, s.animate(h, {
            queue: !1,
            duration: n.duration,
            easing: n.easing,
            complete: function() {
                "hide" === u && s.hide(), e.effects.restore(s, o), e.effects.removeWrapper(s), r();
            }
        });
    };
})(jQuery);

(function(e) {
    e.effects.effect.explode = function(n, r) {
        function i() {
            w.push(this), w.length === h * p && s();
        }
        function s() {
            d.css({
                visibility: "visible"
            }), e(w).remove(), m || d.hide(), r();
        }
        var o, u, a, f, l, c, h = n.pieces ? Math.round(Math.sqrt(n.pieces)) : 3, p = h, d = e(this), v = e.effects.setMode(d, n.mode || "hide"), m = "show" === v, g = d.show().css("visibility", "hidden").offset(), y = Math.ceil(d.outerWidth() / p), b = Math.ceil(d.outerHeight() / h), w = [];
        for (o = 0; h > o; o++) for (f = g.top + o * b, c = o - (h - 1) / 2, u = 0; p > u; u++) a = g.left + u * y, l = u - (p - 1) / 2, d.clone().appendTo("body").wrap("<div></div>").css({
            position: "absolute",
            visibility: "visible",
            left: -u * y,
            top: -o * b
        }).parent().addClass("ui-effects-explode").css({
            position: "absolute",
            overflow: "hidden",
            width: y,
            height: b,
            left: a + (m ? l * y : 0),
            top: f + (m ? c * b : 0),
            opacity: m ? 0 : 1
        }).animate({
            left: a + (m ? 0 : l * y),
            top: f + (m ? 0 : c * b),
            opacity: m ? 1 : 0
        }, n.duration || 500, n.easing, i);
    };
})(jQuery);

(function(e) {
    e.effects.effect.fade = function(n, r) {
        var i = e(this), s = e.effects.setMode(i, n.mode || "toggle");
        i.animate({
            opacity: s
        }, {
            queue: !1,
            duration: n.duration,
            easing: n.easing,
            complete: r
        });
    };
})(jQuery);

(function(e) {
    e.effects.effect.fold = function(n, r) {
        var i, s, o = e(this), u = [ "position", "top", "bottom", "left", "right", "height", "width" ], a = e.effects.setMode(o, n.mode || "hide"), f = "show" === a, l = "hide" === a, c = n.size || 15, h = /([0-9]+)%/.exec(c), p = !!n.horizFirst, d = f !== p, v = d ? [ "width", "height" ] : [ "height", "width" ], m = n.duration / 2, g = {}, y = {};
        e.effects.save(o, u), o.show(), i = e.effects.createWrapper(o).css({
            overflow: "hidden"
        }), s = d ? [ i.width(), i.height() ] : [ i.height(), i.width() ], h && (c = parseInt(h[1], 10) / 100 * s[l ? 0 : 1]), f && i.css(p ? {
            height: 0,
            width: c
        } : {
            height: c,
            width: 0
        }), g[v[0]] = f ? s[0] : c, y[v[1]] = f ? s[1] : 0, i.animate(g, m, n.easing).animate(y, m, n.easing, function() {
            l && o.hide(), e.effects.restore(o, u), e.effects.removeWrapper(o), r();
        });
    };
})(jQuery);

(function(e) {
    e.effects.effect.highlight = function(n, r) {
        var i = e(this), s = [ "backgroundImage", "backgroundColor", "opacity" ], o = e.effects.setMode(i, n.mode || "show"), u = {
            backgroundColor: i.css("backgroundColor")
        };
        "hide" === o && (u.opacity = 0), e.effects.save(i, s), i.show().css({
            backgroundImage: "none",
            backgroundColor: n.color || "#ffff99"
        }).animate(u, {
            queue: !1,
            duration: n.duration,
            easing: n.easing,
            complete: function() {
                "hide" === o && i.hide(), e.effects.restore(i, s), r();
            }
        });
    };
})(jQuery);

(function(e) {
    e.effects.effect.pulsate = function(n, r) {
        var i, s = e(this), o = e.effects.setMode(s, n.mode || "show"), u = "show" === o, a = "hide" === o, f = u || "hide" === o, l = 2 * (n.times || 5) + (f ? 1 : 0), c = n.duration / l, h = 0, p = s.queue(), d = p.length;
        for ((u || !s.is(":visible")) && (s.css("opacity", 0).show(), h = 1), i = 1; l > i; i++) s.animate({
            opacity: h
        }, c, n.easing), h = 1 - h;
        s.animate({
            opacity: h
        }, c, n.easing), s.queue(function() {
            a && s.hide(), r();
        }), d > 1 && p.splice.apply(p, [ 1, 0 ].concat(p.splice(d, l + 1))), s.dequeue();
    };
})(jQuery);

(function(e) {
    e.effects.effect.puff = function(n, r) {
        var i = e(this), s = e.effects.setMode(i, n.mode || "hide"), o = "hide" === s, u = parseInt(n.percent, 10) || 150, a = u / 100, f = {
            height: i.height(),
            width: i.width(),
            outerHeight: i.outerHeight(),
            outerWidth: i.outerWidth()
        };
        e.extend(n, {
            effect: "scale",
            queue: !1,
            fade: !0,
            mode: s,
            complete: r,
            percent: o ? u : 100,
            from: o ? f : {
                height: f.height * a,
                width: f.width * a,
                outerHeight: f.outerHeight * a,
                outerWidth: f.outerWidth * a
            }
        }), i.effect(n);
    }, e.effects.effect.scale = function(n, r) {
        var i = e(this), s = e.extend(!0, {}, n), o = e.effects.setMode(i, n.mode || "effect"), u = parseInt(n.percent, 10) || (0 === parseInt(n.percent, 10) ? 0 : "hide" === o ? 0 : 100), a = n.direction || "both", f = n.origin, l = {
            height: i.height(),
            width: i.width(),
            outerHeight: i.outerHeight(),
            outerWidth: i.outerWidth()
        }, c = {
            y: "horizontal" !== a ? u / 100 : 1,
            x: "vertical" !== a ? u / 100 : 1
        };
        s.effect = "size", s.queue = !1, s.complete = r, "effect" !== o && (s.origin = f || [ "middle", "center" ], s.restore = !0), s.from = n.from || ("show" === o ? {
            height: 0,
            width: 0,
            outerHeight: 0,
            outerWidth: 0
        } : l), s.to = {
            height: l.height * c.y,
            width: l.width * c.x,
            outerHeight: l.outerHeight * c.y,
            outerWidth: l.outerWidth * c.x
        }, s.fade && ("show" === o && (s.from.opacity = 0, s.to.opacity = 1), "hide" === o && (s.from.opacity = 1, s.to.opacity = 0)), i.effect(s);
    }, e.effects.effect.size = function(n, r) {
        var i, s, o, u = e(this), a = [ "position", "top", "bottom", "left", "right", "width", "height", "overflow", "opacity" ], f = [ "position", "top", "bottom", "left", "right", "overflow", "opacity" ], l = [ "width", "height", "overflow" ], c = [ "fontSize" ], h = [ "borderTopWidth", "borderBottomWidth", "paddingTop", "paddingBottom" ], p = [ "borderLeftWidth", "borderRightWidth", "paddingLeft", "paddingRight" ], d = e.effects.setMode(u, n.mode || "effect"), v = n.restore || "effect" !== d, m = n.scale || "both", g = n.origin || [ "middle", "center" ], y = u.css("position"), b = v ? a : f, w = {
            height: 0,
            width: 0,
            outerHeight: 0,
            outerWidth: 0
        };
        "show" === d && u.show(), i = {
            height: u.height(),
            width: u.width(),
            outerHeight: u.outerHeight(),
            outerWidth: u.outerWidth()
        }, "toggle" === n.mode && "show" === d ? (u.from = n.to || w, u.to = n.from || i) : (u.from = n.from || ("show" === d ? w : i), u.to = n.to || ("hide" === d ? w : i)), o = {
            from: {
                y: u.from.height / i.height,
                x: u.from.width / i.width
            },
            to: {
                y: u.to.height / i.height,
                x: u.to.width / i.width
            }
        }, ("box" === m || "both" === m) && (o.from.y !== o.to.y && (b = b.concat(h), u.from = e.effects.setTransition(u, h, o.from.y, u.from), u.to = e.effects.setTransition(u, h, o.to.y, u.to)), o.from.x !== o.to.x && (b = b.concat(p), u.from = e.effects.setTransition(u, p, o.from.x, u.from), u.to = e.effects.setTransition(u, p, o.to.x, u.to))), ("content" === m || "both" === m) && o.from.y !== o.to.y && (b = b.concat(c).concat(l), u.from = e.effects.setTransition(u, c, o.from.y, u.from), u.to = e.effects.setTransition(u, c, o.to.y, u.to)), e.effects.save(u, b), u.show(), e.effects.createWrapper(u), u.css("overflow", "hidden").css(u.from), g && (s = e.effects.getBaseline(g, i), u.from.top = (i.outerHeight - u.outerHeight()) * s.y, u.from.left = (i.outerWidth - u.outerWidth()) * s.x, u.to.top = (i.outerHeight - u.to.outerHeight) * s.y, u.to.left = (i.outerWidth - u.to.outerWidth) * s.x), u.css(u.from), ("content" === m || "both" === m) && (h = h.concat([ "marginTop", "marginBottom" ]).concat(c), p = p.concat([ "marginLeft", "marginRight" ]), l = a.concat(h).concat(p), u.find("*[width]").each(function() {
            var r = e(this), i = {
                height: r.height(),
                width: r.width(),
                outerHeight: r.outerHeight(),
                outerWidth: r.outerWidth()
            };
            v && e.effects.save(r, l), r.from = {
                height: i.height * o.from.y,
                width: i.width * o.from.x,
                outerHeight: i.outerHeight * o.from.y,
                outerWidth: i.outerWidth * o.from.x
            }, r.to = {
                height: i.height * o.to.y,
                width: i.width * o.to.x,
                outerHeight: i.height * o.to.y,
                outerWidth: i.width * o.to.x
            }, o.from.y !== o.to.y && (r.from = e.effects.setTransition(r, h, o.from.y, r.from), r.to = e.effects.setTransition(r, h, o.to.y, r.to)), o.from.x !== o.to.x && (r.from = e.effects.setTransition(r, p, o.from.x, r.from), r.to = e.effects.setTransition(r, p, o.to.x, r.to)), r.css(r.from), r.animate(r.to, n.duration, n.easing, function() {
                v && e.effects.restore(r, l);
            });
        })), u.animate(u.to, {
            queue: !1,
            duration: n.duration,
            easing: n.easing,
            complete: function() {
                0 === u.to.opacity && u.css("opacity", u.from.opacity), "hide" === d && u.hide(), e.effects.restore(u, b), v || ("static" === y ? u.css({
                    position: "relative",
                    top: u.to.top,
                    left: u.to.left
                }) : e.each([ "top", "left" ], function(e, t) {
                    u.css(t, function(t, n) {
                        var r = parseInt(n, 10), i = e ? u.to.left : u.to.top;
                        return "auto" === n ? i + "px" : r + i + "px";
                    });
                })), e.effects.removeWrapper(u), r();
            }
        });
    };
})(jQuery);

(function(e) {
    e.effects.effect.shake = function(n, r) {
        var i, s = e(this), o = [ "position", "top", "bottom", "left", "right", "height", "width" ], u = e.effects.setMode(s, n.mode || "effect"), a = n.direction || "left", f = n.distance || 20, l = n.times || 3, c = 2 * l + 1, h = Math.round(n.duration / c), p = "up" === a || "down" === a ? "top" : "left", d = "up" === a || "left" === a, v = {}, m = {}, g = {}, y = s.queue(), b = y.length;
        for (e.effects.save(s, o), s.show(), e.effects.createWrapper(s), v[p] = (d ? "-=" : "+=") + f, m[p] = (d ? "+=" : "-=") + 2 * f, g[p] = (d ? "-=" : "+=") + 2 * f, s.animate(v, h, n.easing), i = 1; l > i; i++) s.animate(m, h, n.easing).animate(g, h, n.easing);
        s.animate(m, h, n.easing).animate(v, h / 2, n.easing).queue(function() {
            "hide" === u && s.hide(), e.effects.restore(s, o), e.effects.removeWrapper(s), r();
        }), b > 1 && y.splice.apply(y, [ 1, 0 ].concat(y.splice(b, c + 1))), s.dequeue();
    };
})(jQuery);

(function(e) {
    e.effects.effect.slide = function(n, r) {
        var i, s = e(this), o = [ "position", "top", "bottom", "left", "right", "width", "height" ], u = e.effects.setMode(s, n.mode || "show"), a = "show" === u, f = n.direction || "left", l = "up" === f || "down" === f ? "top" : "left", c = "up" === f || "left" === f, h = {};
        e.effects.save(s, o), s.show(), i = n.distance || s["top" === l ? "outerHeight" : "outerWidth"](!0), e.effects.createWrapper(s).css({
            overflow: "hidden"
        }), a && s.css(l, c ? isNaN(i) ? "-" + i : -i : i), h[l] = (a ? c ? "+=" : "-=" : c ? "-=" : "+=") + i, s.animate(h, {
            queue: !1,
            duration: n.duration,
            easing: n.easing,
            complete: function() {
                "hide" === u && s.hide(), e.effects.restore(s, o), e.effects.removeWrapper(s), r();
            }
        });
    };
})(jQuery);

(function(e) {
    e.effects.effect.transfer = function(n, r) {
        var i = e(this), s = e(n.to), o = "fixed" === s.css("position"), u = e("body"), a = o ? u.scrollTop() : 0, f = o ? u.scrollLeft() : 0, l = s.offset(), c = {
            top: l.top - a,
            left: l.left - f,
            height: s.innerHeight(),
            width: s.innerWidth()
        }, h = i.offset(), p = e("<div class='ui-effects-transfer'></div>").appendTo(document.body).addClass(n.className).css({
            top: h.top - a,
            left: h.left - f,
            height: i.innerHeight(),
            width: i.innerWidth(),
            position: o ? "fixed" : "absolute"
        }).animate(c, n.duration, n.easing, function() {
            p.remove(), r();
        });
    };
})(jQuery);