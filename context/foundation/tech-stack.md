---
starter_id: spring
package_manager: maven
project_name: zgrani
hints:
  language_family: java
  team_size: solo
  deployment_target: render
  ci_provider: github-actions
  ci_default_flow: auto-deploy-on-merge
  bootstrapper_confidence: verified
  path_taken: standard
  quality_override: false
  self_check_answers: null
  has_auth: false
  has_payments: false
  has_realtime: false
  has_ai: false
  has_background_jobs: false
---

## Why this stack

Spring Boot jest rekomendacją domyślną dla produktu typu web-app w rodzinie Java i przechodzi wszystkie cztery bramki jakości: jest typowany, konwencyjny, obecny w danych treningowych i udokumentowany. W rejestrze niesie najwyższy poziom pewności scaffoldingu, co przy jednym tygodniu pracy po godzinach waży więcej niż jakakolwiek przewaga architektoniczna alternatyw. Decydujące było jednak co innego: autor pracuje w tym stacku zawodowo, a całe ryzyko projektu leży w regule rankingu i w celowanej prośbie o potwierdzenie, nie w doborze frameworka — stack, którego trzeba się uczyć, przeniósłby to ryzyko na każdą godzinę realizacji. Front powstaje w React i TypeScript poza tym kontraktem, ponieważ hand-off nie ma pola na warstwę kliencką; przyjęta decyzja to jeden artefakt wdrożeniowy, w którym Spring Boot serwuje zbudowany bundel jako zasoby statyczne, a dev-serwer Vite dostaje proxy na backend. Uwierzytelnienia nie ma świadomie — produkt stoi na jednym nieodgadywalnym linku. Flaga zadań w tle pozostaje wyłączona: jedynym kandydatem jest przypomnienie na dzień przed terminem, oznaczone jako nice-to-have i ostatnie w kolejności realizacji, a Spring pokrywa je wbudowanym harmonogramem bez dodatkowej infrastruktury.
