const API_ORIGIN = 'https://code-quest-api-klr7.onrender.com';

export function onRequest({ request }) {
  const incomingUrl = new URL(request.url);
  const upstreamUrl = new URL(API_ORIGIN);
  upstreamUrl.pathname = incomingUrl.pathname;
  upstreamUrl.search = incomingUrl.search;

  const upstreamRequest = new Request(upstreamUrl, request);
  upstreamRequest.headers.delete('Host');

  return fetch(upstreamRequest, { redirect: 'manual' });
}
