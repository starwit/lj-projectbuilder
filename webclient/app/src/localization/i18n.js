import i18n from "i18next";
import {initReactI18next} from "react-i18next";
import LanguageDetector from "i18next-browser-languagedetector";
import translationsDeDE from "./translations-de-DE";
import translationsEnEN from "./translations-en-EN";

const resources = {
    "de-DE": {translation: translationsDeDE},
    "en-US": {translation: translationsEnEN}
};

const lngDetectinOptions = {
    order: ["navigator", "cookie", "localStorage", "querystring", "htmlTag", "path", "subdomain"]
};

i18n
    .use(LanguageDetector)
    .use(initReactI18next)
    .init({
        resources,
        detection: lngDetectinOptions,
        fallbackLng: ["en-US", "de-DE"],
        keySeparator: false,
        interpolation: {
            escapeValue: false
        }
    }, null).then(null, null);

export default i18n;
