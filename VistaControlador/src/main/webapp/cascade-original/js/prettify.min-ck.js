var q = null;

window.PR_SHOULD_USE_CONTINUATION = !0;

(function() {
    function e(e) {
        function t(e) {
            var t = e.charCodeAt(0);
            if (t !== 92) return t;
            var n = e.charAt(1);
            return (t = c[n]) ? t : "0" <= n && n <= "7" ? parseInt(e.substring(1), 8) : n === "u" || n === "x" ? parseInt(e.substring(2), 16) : e.charCodeAt(1);
        }
        function n(e) {
            if (e < 32) return (e < 16 ? "\\x0" : "\\x") + e.toString(16);
            e = String.fromCharCode(e);
            if (e === "\\" || e === "-" || e === "[" || e === "]") e = "\\" + e;
            return e;
        }
        function r(e) {
            for (var r = e.substring(1, e.length - 1).match(/\\u[\dA-Fa-f]{4}|\\x[\dA-Fa-f]{2}|\\[0-3][0-7]{0,2}|\\[0-7]{1,2}|\\[\S\s]|[^\\]/g), e = [], i = [], s = r[0] === "^", o = s ? 1 : 0, u = r.length; o < u; ++o) {
                var a = r[o];
                if (/\\[bdsw]/i.test(a)) e.push(a); else {
                    var a = t(a), f;
                    o + 2 < u && "-" === r[o + 1] ? (f = t(r[o + 2]), o += 2) : f = a;
                    i.push([ a, f ]);
                    f < 65 || a > 122 || (f < 65 || a > 90 || i.push([ Math.max(65, a) | 32, Math.min(f, 90) | 32 ]), f < 97 || a > 122 || i.push([ Math.max(97, a) & -33, Math.min(f, 122) & -33 ]));
                }
            }
            i.sort(function(e, t) {
                return e[0] - t[0] || t[1] - e[1];
            });
            r = [];
            a = [ NaN, NaN ];
            for (o = 0; o < i.length; ++o) u = i[o], u[0] <= a[1] + 1 ? a[1] = Math.max(a[1], u[1]) : r.push(a = u);
            i = [ "[" ];
            s && i.push("^");
            i.push.apply(i, e);
            for (o = 0; o < r.length; ++o) u = r[o], i.push(n(u[0])), u[1] > u[0] && (u[1] + 1 > u[0] && i.push("-"), i.push(n(u[1])));
            i.push("]");
            return i.join("");
        }
        function i(e) {
            for (var t = e.source.match(/\[(?:[^\\\]]|\\[\S\s])*]|\\u[\dA-Fa-f]{4}|\\x[\dA-Fa-f]{2}|\\\d+|\\[^\dux]|\(\?[!:=]|[()^]|[^()[\\^]+/g), n = t.length, i = [], u = 0, a = 0; u < n; ++u) {
                var f = t[u];
                f === "(" ? ++a : "\\" === f.charAt(0) && (f = +f.substring(1)) && f <= a && (i[f] = -1);
            }
            for (u = 1; u < i.length; ++u) -1 === i[u] && (i[u] = ++s);
            for (a = u = 0; u < n; ++u) f = t[u], f === "(" ? (++a, i[a] === void 0 && (t[u] = "(?:")) : "\\" === f.charAt(0) && (f = +f.substring(1)) && f <= a && (t[u] = "\\" + i[a]);
            for (a = u = 0; u < n; ++u) "^" === t[u] && "^" !== t[u + 1] && (t[u] = "");
            if (e.ignoreCase && o) for (u = 0; u < n; ++u) f = t[u], e = f.charAt(0), f.length >= 2 && e === "[" ? t[u] = r(f) : e !== "\\" && (t[u] = f.replace(/[A-Za-z]/g, function(e) {
                e = e.charCodeAt(0);
                return "[" + String.fromCharCode(e & -33, e | 32) + "]";
            }));
            return t.join("");
        }
        for (var s = 0, o = !1, u = !1, a = 0, f = e.length; a < f; ++a) {
            var l = e[a];
            if (l.ignoreCase) u = !0; else if (/[a-z]/i.test(l.source.replace(/\\u[\da-f]{4}|\\x[\da-f]{2}|\\[^UXux]/gi, ""))) {
                o = !0;
                u = !1;
                break;
            }
        }
        for (var c = {
            b: 8,
            t: 9,
            n: 10,
            v: 11,
            f: 12,
            r: 13
        }, h = [], a = 0, f = e.length; a < f; ++a) {
            l = e[a];
            if (l.global || l.multiline) throw Error("" + l);
            h.push("(?:" + i(l) + ")");
        }
        return RegExp(h.join("|"), u ? "gi" : "g");
    }
    function t(e) {
        function t(e) {
            switch (e.nodeType) {
              case 1:
                if (n.test(e.className)) break;
                for (var u = e.firstChild; u; u = u.nextSibling) t(u);
                u = e.nodeName;
                if ("BR" === u || "LI" === u) r[o] = "\n", s[o << 1] = i++, s[o++ << 1 | 1] = e;
                break;
              case 3:
              case 4:
                u = e.nodeValue, u.length && (u = a ? u.replace(/\r\n?/g, "\n") : u.replace(/[\t\n\r ]+/g, " "), r[o] = u, s[o << 1] = i, i += u.length, s[o++ << 1 | 1] = e);
            }
        }
        var n = /(?:^|\s)nocode(?:\s|$)/, r = [], i = 0, s = [], o = 0, u;
        e.currentStyle ? u = e.currentStyle.whiteSpace : window.getComputedStyle && (u = document.defaultView.getComputedStyle(e, q).getPropertyValue("white-space"));
        var a = u && "pre" === u.substring(0, 3);
        t(e);
        return {
            a: r.join("").replace(/\n$/, ""),
            c: s
        };
    }
    function n(e, t, n, r) {
        t && (e = {
            a: t,
            d: e
        }, n(e), r.push.apply(r, e.e));
    }
    function r(t, r) {
        function i(e) {
            for (var t = e.d, f = [ t, "pln" ], l = 0, c = e.a.match(o) || [], h = {}, p = 0, d = c.length; p < d; ++p) {
                var v = c[p], m = h[v], g = void 0, y;
                if (typeof m == "string") y = !1; else {
                    var b = s[v.charAt(0)];
                    if (b) g = v.match(b[1]), m = b[0]; else {
                        for (y = 0; y < a; ++y) if (b = r[y], g = v.match(b[1])) {
                            m = b[0];
                            break;
                        }
                        g || (m = "pln");
                    }
                    (y = m.length >= 5 && "lang-" === m.substring(0, 5)) && (!g || typeof g[1] != "string") && (y = !1, m = "src");
                    y || (h[v] = m);
                }
                b = l;
                l += v.length;
                if (y) {
                    y = g[1];
                    var w = v.indexOf(y), E = w + y.length;
                    g[2] && (E = v.length - g[2].length, w = E - y.length);
                    m = m.substring(5);
                    n(t + b, v.substring(0, w), i, f);
                    n(t + b + w, y, u(m, y), f);
                    n(t + b + E, v.substring(E), i, f);
                } else f.push(t + b, m);
            }
            e.e = f;
        }
        var s = {}, o;
        (function() {
            for (var n = t.concat(r), i = [], u = {}, a = 0, f = n.length; a < f; ++a) {
                var l = n[a], c = l[3];
                if (c) for (var h = c.length; --h >= 0; ) s[c.charAt(h)] = l;
                l = l[1];
                c = "" + l;
                u.hasOwnProperty(c) || (i.push(l), u[c] = q);
            }
            i.push(/[\S\s]/);
            o = e(i);
        })();
        var a = r.length;
        return i;
    }
    function i(e) {
        var t = [], n = [];
        e.tripleQuotedStrings ? t.push([ "str", /^(?:'''(?:[^'\\]|\\[\S\s]|''?(?=[^']))*(?:'''|$)|"""(?:[^"\\]|\\[\S\s]|""?(?=[^"]))*(?:"""|$)|'(?:[^'\\]|\\[\S\s])*(?:'|$)|"(?:[^"\\]|\\[\S\s])*(?:"|$))/, q, "'\"" ]) : e.multiLineStrings ? t.push([ "str", /^(?:'(?:[^'\\]|\\[\S\s])*(?:'|$)|"(?:[^"\\]|\\[\S\s])*(?:"|$)|`(?:[^\\`]|\\[\S\s])*(?:`|$))/, q, "'\"`" ]) : t.push([ "str", /^(?:'(?:[^\n\r'\\]|\\.)*(?:'|$)|"(?:[^\n\r"\\]|\\.)*(?:"|$))/, q, "\"'" ]);
        e.verbatimStrings && n.push([ "str", /^@"(?:[^"]|"")*(?:"|$)/, q ]);
        var i = e.hashComments;
        i && (e.cStyleComments ? (i > 1 ? t.push([ "com", /^#(?:##(?:[^#]|#(?!##))*(?:###|$)|.*)/, q, "#" ]) : t.push([ "com", /^#(?:(?:define|elif|else|endif|error|ifdef|include|ifndef|line|pragma|undef|warning)\b|[^\n\r]*)/, q, "#" ]), n.push([ "str", /^<(?:(?:(?:\.\.\/)*|\/?)(?:[\w-]+(?:\/[\w-]+)+)?[\w-]+\.h|[a-z]\w*)>/, q ])) : t.push([ "com", /^#[^\n\r]*/, q, "#" ]));
        e.cStyleComments && (n.push([ "com", /^\/\/[^\n\r]*/, q ]), n.push([ "com", /^\/\*[\S\s]*?(?:\*\/|$)/, q ]));
        e.regexLiterals && n.push([ "lang-regex", /^(?:^^\.?|[!+-]|!=|!==|#|%|%=|&|&&|&&=|&=|\(|\*|\*=|\+=|,|-=|->|\/|\/=|:|::|;|<|<<|<<=|<=|=|==|===|>|>=|>>|>>=|>>>|>>>=|[?@[^]|\^=|\^\^|\^\^=|{|\||\|=|\|\||\|\|=|~|break|case|continue|delete|do|else|finally|instanceof|return|throw|try|typeof)\s*(\/(?=[^*/])(?:[^/[\\]|\\[\S\s]|\[(?:[^\\\]]|\\[\S\s])*(?:]|$))+\/)/ ]);
        (i = e.types) && n.push([ "typ", i ]);
        e = ("" + e.keywords).replace(/^ | $/g, "");
        e.length && n.push([ "kwd", RegExp("^(?:" + e.replace(/[\s,]+/g, "|") + ")\\b"), q ]);
        t.push([ "pln", /^\s+/, q, " \r\n	??" ]);
        n.push([ "lit", /^@[$_a-z][\w$@]*/i, q ], [ "typ", /^(?:[@_]?[A-Z]+[a-z][\w$@]*|\w+_t\b)/, q ], [ "pln", /^[$_a-z][\w$@]*/i, q ], [ "lit", /^(?:0x[\da-f]+|(?:\d(?:_\d+)*\d*(?:\.\d*)?|\.\d\+)(?:e[+-]?\d+)?)[a-z]*/i, q, "0123456789" ], [ "pln", /^\\[\S\s]?/, q ], [ "pun", /^.[^\s\w"-$'./@\\`]*/, q ]);
        return r(t, n);
    }
    function s(e, t) {
        function n(e) {
            switch (e.nodeType) {
              case 1:
                if (i.test(e.className)) break;
                if ("BR" === e.nodeName) r(e), e.parentNode && e.parentNode.removeChild(e); else for (e = e.firstChild; e; e = e.nextSibling) n(e);
                break;
              case 3:
              case 4:
                if (a) {
                    var t = e.nodeValue, u = t.match(s);
                    if (u) {
                        var f = t.substring(0, u.index);
                        e.nodeValue = f;
                        (t = t.substring(u.index + u[0].length)) && e.parentNode.insertBefore(o.createTextNode(t), e.nextSibling);
                        r(e);
                        f || e.parentNode.removeChild(e);
                    }
                }
            }
        }
        function r(e) {
            function t(e, n) {
                var r = n ? e.cloneNode(!1) : e, i = e.parentNode;
                if (i) {
                    var i = t(i, 1), s = e.nextSibling;
                    i.appendChild(r);
                    for (var o = s; o; o = s) s = o.nextSibling, i.appendChild(o);
                }
                return r;
            }
            for (; !e.nextSibling; ) if (e = e.parentNode, !e) return;
            for (var e = t(e.nextSibling, 0), n; (n = e.parentNode) && n.nodeType === 1; ) e = n;
            f.push(e);
        }
        var i = /(?:^|\s)nocode(?:\s|$)/, s = /\r\n?|\n/, o = e.ownerDocument, u;
        e.currentStyle ? u = e.currentStyle.whiteSpace : window.getComputedStyle && (u = o.defaultView.getComputedStyle(e, q).getPropertyValue("white-space"));
        var a = u && "pre" === u.substring(0, 3);
        for (u = o.createElement("LI"); e.firstChild; ) u.appendChild(e.firstChild);
        for (var f = [ u ], l = 0; l < f.length; ++l) n(f[l]);
        t === (t | 0) && f[0].setAttribute("value", t);
        var c = o.createElement("OL");
        c.className = "linenums";
        for (var h = Math.max(0, t - 1 | 0) || 0, l = 0, p = f.length; l < p; ++l) u = f[l], u.className = "L" + (l + h) % 10, u.firstChild || u.appendChild(o.createTextNode("??")), c.appendChild(u);
        e.appendChild(c);
    }
    function o(e, t) {
        for (var n = t.length; --n >= 0; ) {
            var r = t[n];
            b.hasOwnProperty(r) ? window.console && console.warn("cannot override language handler %s", r) : b[r] = e;
        }
    }
    function u(e, t) {
        if (!e || !b.hasOwnProperty(e)) e = /^\s*</.test(t) ? "default-markup" : "default-code";
        return b[e];
    }
    function a(e) {
        var n = e.g;
        try {
            var r = t(e.h), i = r.a;
            e.a = i;
            e.c = r.c;
            e.d = 0;
            u(n, i)(e);
            var s = /\bMSIE\b/.test(navigator.userAgent), n = /\n/g, o = e.a, a = o.length, r = 0, f = e.c, l = f.length, i = 0, c = e.e, h = c.length, e = 0;
            c[h] = a;
            var p, d;
            for (d = p = 0; d < h; ) c[d] !== c[d + 2] ? (c[p++] = c[d++], c[p++] = c[d++]) : d += 2;
            h = p;
            for (d = p = 0; d < h; ) {
                for (var v = c[d], m = c[d + 1], g = d + 2; g + 2 <= h && c[g + 1] === m; ) g += 2;
                c[p++] = v;
                c[p++] = m;
                d = g;
            }
            for (c.length = p; i < l; ) {
                var y = f[i + 2] || a, b = c[e + 2] || a, g = Math.min(y, b), w = f[i + 1], E;
                if (w.nodeType !== 1 && (E = o.substring(r, g))) {
                    s && (E = E.replace(n, "\r"));
                    w.nodeValue = E;
                    var S = w.ownerDocument, x = S.createElement("SPAN");
                    x.className = c[e + 1];
                    var T = w.parentNode;
                    T.replaceChild(x, w);
                    x.appendChild(w);
                    r < y && (f[i + 1] = w = S.createTextNode(o.substring(g, y)), T.insertBefore(w, x.nextSibling));
                }
                r = g;
                r >= y && (i += 2);
                r >= b && (e += 2);
            }
        } catch (N) {
            "console" in window && console.log(N && N.stack ? N.stack : N);
        }
    }
    var f = [ "break,continue,do,else,for,if,return,while" ], l = [ [ f, "auto,case,char,const,default,double,enum,extern,float,goto,int,long,register,short,signed,sizeof,static,struct,switch,typedef,union,unsigned,void,volatile" ], "catch,class,delete,false,import,new,operator,private,protected,public,this,throw,true,try,typeof" ], c = [ l, "alignof,align_union,asm,axiom,bool,concept,concept_map,const_cast,constexpr,decltype,dynamic_cast,explicit,export,friend,inline,late_check,mutable,namespace,nullptr,reinterpret_cast,static_assert,static_cast,template,typeid,typename,using,virtual,where" ], h = [ l, "abstract,boolean,byte,extends,final,finally,implements,import,instanceof,null,native,package,strictfp,super,synchronized,throws,transient" ], p = [ h, "as,base,by,checked,decimal,delegate,descending,dynamic,event,fixed,foreach,from,group,implicit,in,interface,internal,into,is,lock,object,out,override,orderby,params,partial,readonly,ref,sbyte,sealed,stackalloc,string,select,uint,ulong,unchecked,unsafe,ushort,var" ], l = [ l, "debugger,eval,export,function,get,null,set,undefined,var,with,Infinity,NaN" ], d = [ f, "and,as,assert,class,def,del,elif,except,exec,finally,from,global,import,in,is,lambda,nonlocal,not,or,pass,print,raise,try,with,yield,False,True,None" ], v = [ f, "alias,and,begin,case,class,def,defined,elsif,end,ensure,false,in,module,next,nil,not,or,redo,rescue,retry,self,super,then,true,undef,unless,until,when,yield,BEGIN,END" ], f = [ f, "case,done,elif,esac,eval,fi,function,in,local,set,then,until" ], m = /^(DIR|FILE|vector|(de|priority_)?queue|list|stack|(const_)?iterator|(multi)?(set|map)|bitset|u?(int|float)\d*)/, g = /\S/, y = i({
        keywords: [ c, p, l, "caller,delete,die,do,dump,elsif,eval,exit,foreach,for,goto,if,import,last,local,my,next,no,our,print,package,redo,require,sub,undef,unless,until,use,wantarray,while,BEGIN,END" + d, v, f ],
        hashComments: !0,
        cStyleComments: !0,
        multiLineStrings: !0,
        regexLiterals: !0
    }), b = {};
    o(y, [ "default-code" ]);
    o(r([], [ [ "pln", /^[^<?]+/ ], [ "dec", /^<!\w[^>]*(?:>|$)/ ], [ "com", /^<\!--[\S\s]*?(?:--\>|$)/ ], [ "lang-", /^<\?([\S\s]+?)(?:\?>|$)/ ], [ "lang-", /^<%([\S\s]+?)(?:%>|$)/ ], [ "pun", /^(?:<[%?]|[%?]>)/ ], [ "lang-", /^<xmp\b[^>]*>([\S\s]+?)<\/xmp\b[^>]*>/i ], [ "lang-js", /^<script\b[^>]*>([\S\s]*?)(<\/script\b[^>]*>)/i ], [ "lang-css", /^<style\b[^>]*>([\S\s]*?)(<\/style\b[^>]*>)/i ], [ "lang-in.tag", /^(<\/?[a-z][^<>]*>)/i ] ]), [ "default-markup", "htm", "html", "mxml", "xhtml", "xml", "xsl" ]);
    o(r([ [ "pln", /^\s+/, q, " 	\r\n" ], [ "atv", /^(?:"[^"]*"?|'[^']*'?)/, q, "\"'" ] ], [ [ "tag", /^^<\/?[a-z](?:[\w-.:]*\w)?|\/?>$/i ], [ "atn", /^(?!style[\s=]|on)[a-z](?:[\w:-]*\w)?/i ], [ "lang-uq.val", /^=\s*([^\s"'>]*(?:[^\s"'/>]|\/(?=\s)))/ ], [ "pun", /^[/<->]+/ ], [ "lang-js", /^on\w+\s*=\s*"([^"]+)"/i ], [ "lang-js", /^on\w+\s*=\s*'([^']+)'/i ], [ "lang-js", /^on\w+\s*=\s*([^\s"'>]+)/i ], [ "lang-css", /^style\s*=\s*"([^"]+)"/i ], [ "lang-css", /^style\s*=\s*'([^']+)'/i ], [ "lang-css", /^style\s*=\s*([^\s"'>]+)/i ] ]), [ "in.tag" ]);
    o(r([], [ [ "atv", /^[\S\s]+/ ] ]), [ "uq.val" ]);
    o(i({
        keywords: c,
        hashComments: !0,
        cStyleComments: !0,
        types: m
    }), [ "c", "cc", "cpp", "cxx", "cyc", "m" ]);
    o(i({
        keywords: "null,true,false"
    }), [ "json" ]);
    o(i({
        keywords: p,
        hashComments: !0,
        cStyleComments: !0,
        verbatimStrings: !0,
        types: m
    }), [ "cs" ]);
    o(i({
        keywords: h,
        cStyleComments: !0
    }), [ "java" ]);
    o(i({
        keywords: f,
        hashComments: !0,
        multiLineStrings: !0
    }), [ "bsh", "csh", "sh" ]);
    o(i({
        keywords: d,
        hashComments: !0,
        multiLineStrings: !0,
        tripleQuotedStrings: !0
    }), [ "cv", "py" ]);
    o(i({
        keywords: "caller,delete,die,do,dump,elsif,eval,exit,foreach,for,goto,if,import,last,local,my,next,no,our,print,package,redo,require,sub,undef,unless,until,use,wantarray,while,BEGIN,END",
        hashComments: !0,
        multiLineStrings: !0,
        regexLiterals: !0
    }), [ "perl", "pl", "pm" ]);
    o(i({
        keywords: v,
        hashComments: !0,
        multiLineStrings: !0,
        regexLiterals: !0
    }), [ "rb" ]);
    o(i({
        keywords: l,
        cStyleComments: !0,
        regexLiterals: !0
    }), [ "js" ]);
    o(i({
        keywords: "all,and,by,catch,class,else,extends,false,finally,for,if,in,is,isnt,loop,new,no,not,null,of,off,on,or,return,super,then,true,try,unless,until,when,while,yes",
        hashComments: 3,
        cStyleComments: !0,
        multilineStrings: !0,
        tripleQuotedStrings: !0,
        regexLiterals: !0
    }), [ "coffee" ]);
    o(r([], [ [ "str", /^[\S\s]+/ ] ]), [ "regex" ]);
    window.prettyPrintOne = function(e, t, n) {
        var r = document.createElement("PRE");
        r.innerHTML = e;
        n && s(r, n);
        a({
            g: t,
            i: n,
            h: r
        });
        return r.innerHTML;
    };
    window.prettyPrint = function(e) {
        function t() {
            for (var n = window.PR_SHOULD_USE_CONTINUATION ? f.now() + 250 : Infinity; l < r.length && f.now() < n; l++) {
                var i = r[l], o = i.className;
                if (o.indexOf("prettyprint") >= 0) {
                    var o = o.match(h), u, p;
                    if (p = !o) {
                        p = i;
                        for (var d = void 0, v = p.firstChild; v; v = v.nextSibling) var m = v.nodeType, d = m === 1 ? d ? p : v : m === 3 ? g.test(v.nodeValue) ? p : d : d;
                        p = (u = d === p ? void 0 : d) && "CODE" === u.tagName;
                    }
                    p && (o = u.className.match(h));
                    o && (o = o[1]);
                    p = !1;
                    for (d = i.parentNode; d; d = d.parentNode) if ((d.tagName === "pre" || d.tagName === "code" || d.tagName === "xmp") && d.className && d.className.indexOf("prettyprint") >= 0) {
                        p = !0;
                        break;
                    }
                    p || ((p = (p = i.className.match(/\blinenums\b(?::(\d+))?/)) ? p[1] && p[1].length ? +p[1] : !0 : !1) && s(i, p), c = {
                        g: o,
                        h: i,
                        i: p
                    }, a(c));
                }
            }
            l < r.length ? setTimeout(t, 250) : e && e();
        }
        for (var n = [ document.getElementsByTagName("pre"), document.getElementsByTagName("code"), document.getElementsByTagName("xmp") ], r = [], i = 0; i < n.length; ++i) for (var o = 0, u = n[i].length; o < u; ++o) r.push(n[i][o]);
        var n = q, f = Date;
        f.now || (f = {
            now: function() {
                return +(new Date);
            }
        });
        var l = 0, c, h = /\blang(?:uage)?-([\w.]+)(?!\S)/;
        t();
    };
    window.PR = {
        createSimpleLexer: r,
        registerLangHandler: o,
        sourceDecorator: i,
        PR_ATTRIB_NAME: "atn",
        PR_ATTRIB_VALUE: "atv",
        PR_COMMENT: "com",
        PR_DECLARATION: "dec",
        PR_KEYWORD: "kwd",
        PR_LITERAL: "lit",
        PR_NOCODE: "nocode",
        PR_PLAIN: "pln",
        PR_PUNCTUATION: "pun",
        PR_SOURCE: "src",
        PR_STRING: "str",
        PR_TAG: "tag",
        PR_TYPE: "typ"
    };
})();