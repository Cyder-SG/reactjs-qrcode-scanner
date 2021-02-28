module.exports = {
    "env": {
        "browser": true,
        "node": true,
        "es6": true
    },
    "extends": [
        'airbnb-base',
        'plugin:react/recommended',
        // 'plugin:prettier/recommended',
    ],
    "parserOptions": {
        "ecmaFeatures": {
            "experimentalObjectRestSpread": true,
            "jsx": true
        },
        "sourceType": "module"
    },
    "plugins": [
        "react"
    ],
    "rules": {
        "max-len": [1, {"code": 140, "tabWidth": 4, "ignoreUrls": true}],
        "indent": [
            "error",
            4,
            { "SwitchCase": 1 }
        ],
        "linebreak-style": [
            "error",
            "unix"
        ],
        "react/prop-types": [0],
        "react/jsx-uses-vars": [2],
        "no-console": "warn",
        "no-unreachable": "warn",
        'lines-between-class-members': 0,
        "react/display-name": [true, { "ignoreTranspilerName": false }],
        "quotes": [0]
    }
};
