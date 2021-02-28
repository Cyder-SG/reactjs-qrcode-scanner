module.exports = {
    singleQuote: true,
    tabWidth: 4,
    printWidth: 140,
    trailingComma: 'all',
    overrides: [
        {
            files: ['package.json', '*.scss'],
            options: {
                tabWidth: 2,
            },
        },
        {
            files: ['src/actions/constants/**', 'src/routes/**'],
            options: {
                printWidth: 999,
            },
        },
    ],
};
