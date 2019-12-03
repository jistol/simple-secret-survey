export function a11yProps(prefix) {
    return index => ({
        id: `${prefix}-${index}`,
        key: `${prefix}-key-${index}`
    });
}

