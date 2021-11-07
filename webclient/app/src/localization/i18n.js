import i18n from 'i18next';
import {initReactI18next} from "react-i18next";
import LanguageDetector from "i18next-browser-languagedetector";
import translationsDeDE from "./translations-de-DE";


const resources = {
    "de-DE": {translation: translationsDeDE},
    "en-US": {translation: {}}
};

const lngDetectinOptions = {
    order: ['navigator', 'cookie', 'localStorage', 'querystring', 'htmlTag', 'path', 'subdomain']
};

i18n
    .use(LanguageDetector)
    .use(initReactI18next)
    .init({
        resources,
        detection: lngDetectinOptions,
        fallbackLng: ["de-DE", "en-US"],
        keySeparator: false,
        interpolation: {
            escapeValue: false
        }
    }, null).then(null, null);

export default i18n;