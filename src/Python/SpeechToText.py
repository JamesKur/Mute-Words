def transcribeAudio(audioTrackPath, start, stop):
    import os
    import io
    import ChannelTools
    import numpy
    from google.api_core import operation
    from google.cloud import speech_v1

    os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = 'C:/Users/james/Documents/Visual Studio Code/Java/quantum-petal-319304-dbfb7a8d04ad.json'
    client = speech_v1.SpeechClient()

    newPath = ChannelTools.trimAudio(audioTrackPath,start,stop)

    with io.open(newPath,"rb") as audio_file:
        content = audio_file.read()

    audio = speech_v1.RecognitionAudio(content=content)

    config = speech_v1.RecognitionConfig(
        encoding=speech_v1.RecognitionConfig.AudioEncoding.FLAC,
        sample_rate_hertz=48000,
        language_code="en-US",
        model="video",
        enable_word_time_offsets=True,
        profanity_filter=False
    )

    response = client.recognize(config=config, audio=audio)

    for result in response.results:
            alternative = result.alternatives[0]
            for word_info in alternative.words:
                word = word_info.word
                start_time = word_info.start_time
                end_time = word_info.end_time
                with open("ConvertedText.txt", "a") as text_file:
                    text_file.write(
                        f"Word: {word}, start_time: {start_time.total_seconds()}, end_time: {end_time.total_seconds()}\n"
                    )
                print(
                    f"Word: {word}, start_time: {start_time.total_seconds()}, end_time: {end_time.total_seconds()}"
                )

transcribeAudio("E:\Cache\gMKVExtract\Pineapple Expressfront_center.flac",1056,1078)