FROM atlassian/default-image:latest

# Install Chrome

RUN echo 'deb http://dl.google.com/linux/chrome/deb/ stable main' > /etc/apt/sources.list.d/chrome.list

RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -

RUN set -x \
    && apt-get update \
    && apt-get install -y \
        xvfb \
        google-chrome-stable

ADD scripts/xvfb-chrome /usr/bin/xvfb-chrome
RUN ln -sf /usr/bin/xvfb-chrome /usr/bin/google-chrome

ENV CHROME_BIN /usr/bin/google-chrome

# Install firefox

RUN set -x \
    && apt-get update \
    && apt-get install -y \
        pkg-mozilla-archive-keyring

RUN echo 'deb http://mozilla.debian.net/ jessie-backports firefox-esr' >> /etc/apt/sources.list.d/jessie-backports.list

RUN set -x \
    && apt-get update \
    && apt-get install -y \
        xvfb \
    && apt-get install -y -t \
        jessie-backports \
        firefox-esr

ADD scripts/xvfb-firefox /usr/bin/xvfb-firefox
RUN ln -sf /usr/bin/xvfb-firefox /usr/bin/firefox

ENV FIREFOX_BIN /usr/bin/firefox

# This is needed for PhantomJS
RUN set -x && \
    apt-get update && \
    apt-get install -y \
        bzip2

# RUN java -version
# RUN mvn -v
# RUN apt-cache policy firefox-esr | grep Installed | sed -e "s/Installed/Firefox/"
# RUN apt-cache policy chromium | grep Installed | sed -e "s/Installed/Chrome/"